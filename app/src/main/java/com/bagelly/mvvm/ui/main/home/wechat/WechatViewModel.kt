package com.bagelly.mvvm.ui.main.home.wechat

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.bean.Category
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.CollectRepository
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.project
 * @ClassName: ProjectModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/11 上午9:20
 */
class WechatViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
        const val INITIAL_CHECKED = 0
    }

    private val wechatResoitory by lazy { WechatResoitory() }
    private val collectRepository by lazy { CollectRepository() }

    val cateories: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val checkedCategory: MutableLiveData<Int> = MutableLiveData()
    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val reloadListStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE + 1

    fun getProjectCategory() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val categoryList = wechatResoitory.getWechatCategories()
                val checkedPosition = INITIAL_CHECKED
                val cid = categoryList[checkedPosition].id
                val pagination = wechatResoitory.getWechatArticleList(INITIAL_PAGE, cid)
                page = pagination.curPage

                cateories.value = categoryList
                checkedCategory.value = checkedPosition
                articleList.value = pagination.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadListStatus.value = true
            }
        )

    }
        fun refreshProjectList(checkedPosition: Int = checkedCategory.value ?: INITIAL_PAGE) {
             refreshStatus.value=true
             reloadListStatus.value=false
            if (checkedPosition!=checkedCategory.value){
                articleList.value= mutableListOf()
                checkedCategory.value=checkedPosition
            }

            launch(
                block = {
                    val categoryList=cateories.value?:return@launch
                    val cid=categoryList[checkedPosition].id
                    val pagination=wechatResoitory.getWechatArticleList(INITIAL_PAGE,cid)
                    page=pagination.curPage
                    articleList.value=pagination.datas.toMutableList()
                    refreshStatus.value=false
                },
                error = {
                     refreshStatus.value=false
                     reloadStatus.value=articleList.value?.isEmpty()
                }
            )
        }


    fun loadMorePorjectList(){
        loadMoreStatus.value= LoadMoreStatus.LOADING
        launch(
            block = {
                val categoryList=cateories.value?:return@launch
                val checkedPosition=checkedCategory.value?:return@launch
                val cid=categoryList[checkedPosition].id
                val pagination = wechatResoitory.getWechatArticleList(page+1, cid)
                page=pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value=currentList
                loadMoreStatus.value = if (pagination.offset >= pagination.total) LoadMoreStatus.END else LoadMoreStatus.COMPLETED
            },
            error = {
                loadMoreStatus.value=LoadMoreStatus.ERROR
            }
        )
    }

    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply { if (!collectIds.contains(id)) collectIds.add(id) })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED,id to true)
            },error = {
                updateItemCollectState( id to false)
            }
        )
    }


    fun uncollect(id: Int){
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply { if (!collectIds.contains(id)) collectIds.remove(id) })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED,id to true)
            },error = {
                updateItemCollectState( id to false)
            }
        )
    }
    /**
     * 更新Item的收藏状态
     */
     fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list=articleList.value
         val item=list?.find { it.id==target.first}?:return
         item.collect=target.second
         articleList.value=list
    }

    fun undateListCollectState(){
        val list=articleList.value
        if (list.isNullOrEmpty()) return

        if (userRepository.isLogin()){
            val collectIds=userRepository.getUserInfo()?.collectIds?:return
            list.forEach{it.collect=collectIds.contains(it.id)}
        }else{
            list.forEach{it.collect=false}
        }

        articleList.value=list
    }
}