package com.bagelly.mvvm.ui.points.rank
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_points_rank.*
import kotlinx.android.synthetic.main.include_reload.*
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.points.rank
 * @ClassName: PointsRankActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午1:15
 */
class PointsRankActivity : BaseVmActivity<PointRankViewModel>() {
    private lateinit var mAdapter: PointsRandAdapter

    override fun layoutRes() = R.layout.activity_points_rank

    override fun viewModelClass() = PointRankViewModel::class.java

    override fun initView() {
        mAdapter=PointsRandAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({mViewModel.loaeMoreData()},recyclerView)
        }

        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshData() }
        }

        ivBack.setOnClickListener { ActivityManager.finish(PointsRankActivity::class.java) }
        tvTitle.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        btnReload.setOnClickListener { mViewModel.refreshData() }
    }

    override fun initData() {
        mViewModel.refreshData()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            pointsRank.observe(this@PointsRankActivity, Observer {
                mAdapter.setNewData(it)
            })

            refreshStatus.observe(this@PointsRankActivity, Observer {
                swipeRefreshLayout.isRefreshing=it
            })
            loadMoreStatus.observe(this@PointsRankActivity, Observer {
                when(it){
                    LoadMoreStatus.COMPLETED ->mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR  ->mAdapter.loadMoreFail()
                    LoadMoreStatus.END ->mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })

            reloadStatus.observe(this@PointsRankActivity, Observer {
                reloadView.isVisible=it
            })
        }
    }
}