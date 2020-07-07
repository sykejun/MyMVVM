package com.bagelly.mvvm.ui.points.rank
import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.PointRank
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
/**
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.points.rank
 * @ClassName: PointRankViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午5:10
 */
class PointRankViewModel : BaseViewModel() {
    companion object {
        const val INITIAL_PAGE = 0
    }

    private val pointsRankRespository by lazy { PointsRankRespository() }

    val pointsRank = MutableLiveData<MutableList<PointRank>>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    private var page = INITIAL_PAGE
    fun refreshData() {
        refreshStatus.value=true
        reloadStatus.value=false
        launch(
            block = {
                val pagination =pointsRankRespository.getPointsRank(page)
                page=pagination.curPage
                pointsRank.value=pagination.datas.toMutableList()
                refreshStatus.value=false
            },error = {
                reloadStatus.value=page== INITIAL_PAGE
            }
        )
    }

    fun loaeMoreData(){
        loadMoreStatus.value= LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination=pointsRankRespository.getPointsRank(page)
                page=pagination.curPage
                pointsRank.value?.addAll(pagination.datas)
                loadMoreStatus.value=if (pagination.offset>=pagination.total) LoadMoreStatus.END else LoadMoreStatus.COMPLETED
            },error = {
                loadMoreStatus.value= LoadMoreStatus.ERROR
            }
        )
    }
}