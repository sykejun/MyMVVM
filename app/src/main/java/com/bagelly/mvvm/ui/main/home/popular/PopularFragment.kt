package com.bagelly.mvvm.ui.main.home.popular

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.ui.main.home.ArticleAdapter
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.popular
 * @ClassName: PopularFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/8 下午2:51
 */
class PopularFragment : BaseVmFragment<PopularViewModel>(), ScrollToTop {
    companion object {
        fun newInstance() = PopularFragment()
    }

    private val mAdapter by lazy { ArticleAdapter(R.layout.item_article) }

    override fun layoutRes(): Int = R.layout.fragment_popular
    override fun viewModelClass(): Class<PopularViewModel> = PopularViewModel::class.java

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshArticleList() }
        }
        mAdapter.apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerview)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList()
            }, recyclerview)

            setOnItemClickListener { _, _, position ->
                val article = mAdapter.data[position]
                // TODO: 2020/6/9 调转到详情
            }

            setOnItemChildClickListener { _, view, position ->
                val article = mAdapter.data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) mViewModel.collect(article.id) else mViewModel.uncollect(
                        article.id
                    )
                }

            }

        }

        btnReload.setOnClickListener {
            mViewModel.refreshArticleList()
        }

    }


    override fun scrollToTop() {
        recyclerview.smoothScrollToPosition(0)
    }

    override fun lazyLoadData() {
        mViewModel.refreshArticleList()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing=it
            })

            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    LoadMoreStatus.COMPLETED ->mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR ->mAdapter.loadMoreFail()
                    LoadMoreStatus.END ->mAdapter.loadMoreEnd()
                    else ->return@Observer
                }
            })

            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible=it
            })
        }


        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED,viewLifecycleOwner){
            mViewModel.updateListCollectState()
        }

        Bus.observe<Pair<Int,Boolean>>(USER_COLLECT_UPDATED,viewLifecycleOwner){
            mViewModel.updateItemCollectState(it)
        }

    }

}
