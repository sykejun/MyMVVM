package com.bagelly.mvvm.ui.points.mine
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.ui.points.rank.PointsRankActivity
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_mine_points.*
import kotlinx.android.synthetic.main.header_mine_points.view.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.points.mine
 * @ClassName: MinePointsActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 上午11:37
 */
class MinePointsActivity:BaseVmActivity<MinePointsViewModel>() {
    private lateinit var mAdapter:MinePointsAdapter
    private lateinit var mHeaderView:View
    override fun viewModelClass()=MinePointsViewModel::class.java
    override fun layoutRes()= R.layout.activity_mine_points

    override fun initView() {
        mHeaderView=LayoutInflater.from(this).inflate(R.layout.header_mine_points,null)
        mAdapter=MinePointsAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({mViewModel.loadMoreRecord()},recyclerView)
        }

        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refresh() }

        }

        ivBack.setOnClickListener { ActivityManager.finish(MinePointsActivity::class.java) }
        ivRank.setOnClickListener { ActivityManager.start(PointsRankActivity::class.java) }
        btnReload.setOnClickListener { mViewModel.refresh()}

    }

    override fun initData() {
        mViewModel.refresh()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            totalPoints.observe(this@MinePointsActivity, Observer {
                if (mAdapter.headerLayoutCount==0){
                    mAdapter.setHeaderView(mHeaderView)
                }
                mHeaderView.tvTotalPoints.text=it.coinCount.toString()
                mHeaderView.tvLevelRank.text=getString(R.string.level_rank, it.level, it.rank)
            })

            pointsList.observe(this@MinePointsActivity, Observer {
                mAdapter.setNewData(it)
            })

            refreshStatus.observe(this@MinePointsActivity, Observer {
                swipeRefreshLayout.isRefreshing=it
            })

            loadMoreStatus.observe(this@MinePointsActivity, Observer {
                when(it){
                    LoadMoreStatus.COMPLETED ->mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR ->mAdapter.loadMoreFail()
                    LoadMoreStatus.END ->mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })

            reloadStatus.observe(this@MinePointsActivity, Observer {
                reloadView.isVisible=it
            })
        }
    }
}