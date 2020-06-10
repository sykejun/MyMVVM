package com.bagelly.mvvm.ui.main.home.plaza

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.CollectRepository
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import java.util.function.BinaryOperator

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.plaza
 * @ClassName: PlazaViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/10 上午10:00
 */
class PlazaViewModel :BaseViewModel(){
    companion object{const val INITIAL_PAGE =0}

    private val plazaRepository by lazy {PlazaRepository()}
    private val collectRepository by lazy {CollectRepository()}

    val articleList:MutableLiveData<MutableList<Article>> = MutableLiveData()
    val loadMoreStatus=MutableLiveData<LoadMoreStatus>()
    val refreshStatus=MutableLiveData<Boolean>()
    val reloadStatus=MutableLiveData<Boolean>()

    private var page= INITIAL_PAGE

    fun refreshArticleList() {
        refreshStatus.value=true
        reloadStatus.value=false
        launch(
            block = {
                val paginaton = plazaRepository.getUserArticleList(INITIAL_PAGE)
                page=paginaton.curPage
                articleList.value=paginaton.datas.toMutableList()
                refreshStatus.value=false
            },
            error = {
                refreshStatus.value=false
                reloadStatus.value=page== INITIAL_PAGE
            }
        )
    }

    fun loadMoreArticleList(){
        loadMoreStatus.value=LoadMoreStatus.LOADING

        launch(
            block ={
                val pagination = plazaRepository.getUserArticleList(page)
                page=pagination.curPage
                val currentList=articleList.value?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value=currentList
                loadMoreStatus.value=if (pagination.offset>=pagination.total) LoadMoreStatus.END else LoadMoreStatus.COMPLETED
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
                Bus.post(USER_COLLECT_UPDATED,id to true)
            },
            error = {
                updateItemCollectState(id to false)
            }
        )
    }


    fun uncollect(id: Int){
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id) })
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATED,id to false)
            },
            error = {
              updateItemCollectState(id to true)
            }
        )
    }
    /**
     * 更新Item的收藏状态
     */
     fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list=articleList.value
        val item=list?.find { it.id== target.first}?:return
        item.collect=target.second
        articleList.value=list
    }
    /**
     * 更新列表收藏状态
     */
    fun updateListCollectState(){
        val list=articleList.value
        if (list.isNullOrEmpty())return
        if (userRepository.isLogin()){
            val collectIds=userRepository.getUserInfo()?.collectIds?:return
            list.forEach { it.collect=collectIds.contains(it.id) }
        }else{
            list.forEach{it.collect=false}
        }

        articleList.value=list
    }

}