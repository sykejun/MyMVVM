package com.bagelly.mvvm.ui.main.system.pager
import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.CollectRepository
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import kotlinx.coroutines.Job
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system.pager
 * @ClassName: SystemPagerViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午5:03
 */

class SystemPagerViewModel :BaseViewModel(){
    companion object{
        const val INITIAL_PAGE = 0
    }

    private val systemPagerReposityory by lazy {SystemPagerReposityory()}
    private val collectRepository by lazy { CollectRepository() }

    val articleList= MutableLiveData<MutableList<Article>>()
    val loadMoreStatus=MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    private var page= INITIAL_PAGE
    private var id:Int=-1;
    private var refreshJob:Job?=null

    fun refreshArticleList(cid:Int){
        if (cid!=id){
          cancelJob(refreshJob)
            id=cid
            articleList.value= mutableListOf()
        }

        refreshStatus.value=true
        reloadStatus.value=false
        refreshJob=launch(
            block = {
                val pagination=systemPagerReposityory.getArticleListByCid(INITIAL_PAGE,cid)
                page=pagination.curPage
                articleList.value=pagination.datas.toMutableList()
                refreshStatus.value=false
            },
            error ={
                refreshStatus.value=false
                reloadStatus.value=articleList.value?.isEmpty()
            }
        )
    }

    fun loadMoreArticleList(cid: Int){
        loadMoreStatus.value= LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination=systemPagerReposityory.getArticleListByCid(page,cid)
                page=pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)

                articleList.value=currentList
                loadMoreStatus.value=if (pagination.offset >= pagination.total) LoadMoreStatus.END else LoadMoreStatus.COMPLETED

            },
            error = {
                loadMoreStatus.value= LoadMoreStatus.ERROR
            }
        )
    }

    fun collect(cid: Int){
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (!collectIds.contains(id)) collectIds.add(id)
                })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED,id to true)
            },error = {
                updateListCollectState()
            }
        )
    }

    fun uncollect(cid: Int){
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateListCollectState()
                Bus.post(USER_COLLECT_UPDATED,id to false)
            },error = {
                updateListCollectState()
            }
        )
    }
    /**
     * 更新列表收藏状态
     */
    open fun updateListCollectState() {
       val list=articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()){
            val collectIds=userRepository.getUserInfo()?.collectIds?:return
            list.forEach { it.collect=collectIds.contains(it.id) }
        }else{
            list.forEach { it.collect=false }
        }
        articleList.value=list
    }
    /**
     * 更新Item的收藏状态
     */
    open fun updateItemCollectState(target: Pair<Int, Boolean>) {
          val list=articleList.value
          val item=list?.find { it.id== target.first}?:return
         item.collect=target.second
          articleList.value=list
    }


}