package com.bagelly.mvvm.ui.search.result

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.CollectRepository
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search.result
 * @ClassName: SearchResultViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/19 下午5:20
 */
class SearchResultViewModel : BaseViewModel(){
    companion object{
        const val INITIAL_PAGE = 0
    }

    private val searchResultRepository by lazy { SearchResultRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val articleList= MutableLiveData<MutableList<Article>>()
    val refreshStatus=MutableLiveData<Boolean>()
    val loadMoreStatus=MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus=MutableLiveData<Boolean>()
    private var currentKeywords=""
    private var page= INITIAL_PAGE

    fun search(keywords:String=currentKeywords){
        if (currentKeywords!=keywords){
            currentKeywords=keywords
            articleList.value= emptyList<Article>().toMutableList()
        }

        refreshStatus.value=true
        emptyStatus.value=false
        reloadStatus.value=false

        launch(
            block = {
                val pagination=searchResultRepository.search(keywords, INITIAL_PAGE)
                page=pagination.curPage
                articleList.value=pagination.datas.toMutableList()
                refreshStatus.value = false
                emptyStatus.value=pagination.datas.isEmpty()
            },
            error = {
                refreshStatus.value=false
                reloadStatus.value=page== INITIAL_PAGE
            }
        )

    }


    fun loadMore(){
        loadMoreStatus.value= LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination=searchResultRepository.search(currentKeywords,page)
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

    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (!collectIds.contains(id)) collectIds.add(id)
                })
                updateItemCollectState(id to true)
            }
        )
    }


    fun uncollect(id: Int){
        launch(
            block = {
            collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATED, id to false)
        },error = {
                updateItemCollectState(id to true)
            })
    }

    open fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list=articleList.value
        val item=list?.find { it.id==target.first }?:return
        item.collect=target.second
        articleList.value=list
    }

    open fun updateListCollectState() {
        val list=articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()){
            val collectIds =userRepository.getUserInfo()?.collectIds?:return
            list.forEach{it.collect=collectIds.contains(it.id) }
        }else{
            list.forEach { it.collect=false }
        }
        articleList.value=list
    }

}