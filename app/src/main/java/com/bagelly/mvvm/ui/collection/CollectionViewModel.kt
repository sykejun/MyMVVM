package com.bagelly.mvvm.ui.collection

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
 * @Package: com.bagelly.mvvm.ui.collection
 * @ClassName: CollectionViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/30 下午4:31
 */
class CollectionViewModel : BaseViewModel(){
    companion object{
        const val INITIAL_PAGE = 0
    }
    private val collectionRepository by lazy { CollectionRepository()}
    private val collectRepository by lazy { CollectRepository() }

    val articleList =MutableLiveData<MutableList<Article>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()
    private var page = INITIAL_PAGE

    fun refresh(){
        refreshStatus.value=true
        emptyStatus.value=false
        reloadStatus.value=false
        launch(
            block = {
                val pagination =collectionRepository.getCollectionList(INITIAL_PAGE)
                pagination.datas.forEach { it.collect=true }
                page=pagination.curPage
                articleList.value=pagination.datas.toMutableList()
                emptyStatus.value=pagination.datas.isEmpty()
                refreshStatus.value=false
            },error = {
                refreshStatus.value=false
                reloadStatus.value=page== INITIAL_PAGE
            }
        )
    }

    fun loadMore(){
        loadMoreStatus.value=LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination=collectionRepository.getCollectionList(page)
                pagination.datas.forEach { it.collect=true }
                page=pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)

                articleList.value=currentList
                loadMoreStatus.value=if (pagination.offset>=pagination.total) LoadMoreStatus.END else LoadMoreStatus.COMPLETED
            },error = {
                loadMoreStatus.value= LoadMoreStatus.ERROR
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

                Bus.post(USER_COLLECT_UPDATED,id to false)
            }
        )
    }


}