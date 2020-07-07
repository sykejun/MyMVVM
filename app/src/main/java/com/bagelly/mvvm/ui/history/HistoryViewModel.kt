package com.bagelly.mvvm.ui.history

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.CollectRepository
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.history
 * @ClassName: HistoryViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/7/7 上午9:23
 */

class HistoryViewModel :BaseViewModel(){
    private val historyRepository by lazy { HistoryRepository() }
    private val collectRepository by lazy { CollectRepository() }
    val articleList= MutableLiveData<MutableList<Article>>()
    val emptyStatus=MutableLiveData<Boolean>()

    fun getData(){
        emptyStatus.value=false
        launch(
            block = {
                val readHistory=historyRepository.getReadHistroy()
                val colletIds=userRepository.getUserInfo()?.collectIds?: emptyList<Int>()
                //跟新收藏状态
                readHistory.forEach {
                    it.collect=colletIds.contains(it.id)
                    articleList.value=readHistory.toMutableList()
                    emptyStatus.value=readHistory.isEmpty()
                }
            }
        )
    }

    fun collect(id:Int){
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (!collectIds.contains(id)) collectIds.add(id)
                })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED,id to true)
            },error = {
                updateItemCollectState(id to false)
            }
        )
    }

    fun uncollect(id:Int){
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED,id to true)
            },error = {
                updateItemCollectState(id to false)
            }
        )
    }

    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()) {
            val collectIds = userRepository.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }
     fun updateItemCollectState(target: Pair<Int, Boolean>) {
         val list=articleList.value
         val item=list?.find { it.id==target.first }?:return
         item.collect=target.second
         articleList.value=list
    }

    fun deleteHistory(article: Article){
        launch(
            block = {
                historyRepository.deleteHistory(article)
            }
        )
    }
}