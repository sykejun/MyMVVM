package com.bagelly.mvvm.ui.shared
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.main.home.ArticleAdapter
import com.bagelly.mvvm.ui.share.ShareActivity
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_shared.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.shared
 * @ClassName: SharedActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午1:19
 */
class SharedActivity : BaseVmActivity<SharedViewModel>() {

    private lateinit var mAdapter: ArticleAdapter

    override fun layoutRes() = R.layout.activity_shared

    override fun viewModelClass() = SharedViewModel::class.java

    override fun initView() {
        mAdapter = ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityManager.start(
                    DetailActivity::class.java, mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }

            setOnItemChildClickListener { _, view, position ->
                val article = data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected != view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collet(article.id)
                    }
                }
            }
            setOnItemLongClickListener { adapter, view, position ->
                AlertDialog.Builder(this@SharedActivity)
                    .setMessage(R.string.confirm_delete_shared)
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                    .setPositiveButton(R.string.confirm) { _, _ ->
                        mViewModel.deleteShared(data[position].id)
                        mAdapter.remove(position)
                        this@SharedActivity.emptyView.isVisible = data.isEmpty()
                    }.show()
                true
            }
            setOnLoadMoreListener({ mViewModel.laodMoreArticleList() }, recyclerView)

        }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshArticleList() }
        }
        btnReload.setOnClickListener { mViewModel.refreshArticleList() }
        ivAdd.setOnClickListener { ActivityManager.start(ShareActivity::class.java) }
        ivBack.setOnClickListener { ActivityManager.finish(SharedActivity::class.java) }
    }

    override fun initData() {
        mViewModel.refreshArticleList()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            articleList.observe(this@SharedActivity, Observer {
                mAdapter.setNewData(it)
            })

            refreshStatus.observe(this@SharedActivity, Observer {
                swipeRefreshLayout.isRefreshing=it
            })

            emptyStatus.observe(this@SharedActivity, Observer {
                emptyView.isVisible=it
            })

            loadMoreStatus.observe(this@SharedActivity, Observer {
                when(it){
                    LoadMoreStatus.COMPLETED ->mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR ->mAdapter.loadMoreFail()
                    LoadMoreStatus.END ->mAdapter.loadMoreEnd()

                }
            })

            reloadStatus.observe(this@SharedActivity, Observer {
                reloadView.isVisible=it
            })
        }


        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, this) {
            mViewModel.updateListCollectState()
        }
        Bus.observe<Pair<Int, Boolean>>(USER_COLLECT_UPDATED, this) {
            mViewModel.updateItemCollectState(it)
        }
    }
}