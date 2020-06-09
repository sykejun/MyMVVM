package com.bagelly.mvvm.ui.main.home.latest

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
 * @Package: com.bagelly.mvvm.ui.main.home.latest
 * @ClassName: LatestViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/9 下午2:39
 */

class LatestViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    private val latestRepository by lazy { LatestRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val articleList = MutableLiveData<MutableList<Article>>(mutableListOf())

    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE

    fun refreshProjectList() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val paginaton = latestRepository.getProjectList(INITIAL_PAGE)
                page = paginaton.curPage
                articleList.value = paginaton.datas.toMutableList()
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                refreshStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMorePorjectList() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val paginaton = latestRepository.getProjectList(page)
                page = paginaton.curPage

                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(paginaton.datas)

                articleList.value = currentList
                loadMoreStatus.value = if (paginaton.offset >= paginaton.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
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
                Bus.post(USER_COLLECT_UPDATED, id to true)
            },
            error = {
                updateItemCollectState(id to true)
            }
        )
    }

    fun uncollect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATED, id to false)

            },
            error = {
                updateItemCollectState(id to true)
            }
        )
    }

    /**
     *更新列表的收藏状态
     */
    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()) {
            val colletIds = userRepository.getUserInfo()?.collectIds ?: return
            list.forEach {
                it.collect = colletIds.contains(it.id)
            }

        } else {
            list.forEach { it.collect = false }
        }

        articleList.value=list
    }

    /**
     * 更新Item的收藏状态
     */
     fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }
}