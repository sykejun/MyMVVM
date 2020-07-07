package com.bagelly.mvvm.ui.points.mine

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.PointRank
import com.bagelly.mvvm.model.bean.PointRecord
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import kotlin.math.min

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.points.mine
 * @ClassName: MinePointsViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午3:44
 */
class MinePointsViewModel :BaseViewModel(){
    companion object{
        const val INITIAL_PAGE = 0
    }

    private val minePointsRespository by lazy {MinePointsRespository()}
    val totalPoints=MutableLiveData<PointRank>()
    val pointsList=MutableLiveData<MutableList<PointRecord>>()
    val loadMoreStatus=MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    var page = INITIAL_PAGE
    fun refresh(){
        refreshStatus.value=true
        reloadStatus.value=false
        launch(
            block = {
                val points=minePointsRespository.getMyPoints()
                val paginaton=minePointsRespository.getPointsRecord(page)
                page=paginaton.curPage
                totalPoints.value=points
                pointsList.value=paginaton.datas.toMutableList()
                refreshStatus.value=false
            },error = {
                refreshStatus.value=false
                reloadStatus.value=page== INITIAL_PAGE
            }
        )
    }

    fun loadMoreRecord(){
        loadMoreStatus.value= LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination=minePointsRespository.getPointsRecord(page+1)
                page=pagination.curPage
                pointsList.value?.addAll(pagination.datas)
                loadMoreStatus.value=if (pagination.offset>=pagination.total) LoadMoreStatus.END else LoadMoreStatus.COMPLETED

            },error = {

                loadMoreStatus.value= LoadMoreStatus.ERROR
            }
        )
    }
}