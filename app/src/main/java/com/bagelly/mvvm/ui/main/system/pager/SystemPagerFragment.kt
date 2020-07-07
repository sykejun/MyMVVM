package com.bagelly.mvvm.ui.main.system.pager

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ext.toIntPx
import com.bagelly.mvvm.model.bean.Category
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.main.home.CategoryAdapter
import com.bagelly.mvvm.ui.main.home.SimpleArticleAdapter
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManager.start
import kotlinx.android.synthetic.main.fragment_search_result.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_wechat.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system.pager
 * @ClassName: SystemPagerFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午2:26
 */
class SystemPagerFragment : BaseVmFragment<SystemPagerViewModel>(), ScrollToTop {

    companion object {
        private const val CATEGORY_LIST = "CATEGORY_LIST"
        fun newInstance(categoryList: ArrayList<Category>): SystemPagerFragment {
            return SystemPagerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(CATEGORY_LIST, categoryList)
                }
            }
        }
    }

    private lateinit var categoryList: List<Category>
    var checkedPosition = 0
    private lateinit var mAdapater: SimpleArticleAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun viewModelClass() = SystemPagerViewModel::class.java

    override fun layoutRes() = R.layout.fragment_system_pager
    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshArticleList(categoryList[checkedPosition].id) }
        }

        categoryList = arguments?.getParcelableArrayList(CATEGORY_LIST)!!
        checkedPosition = 0
        categoryAdapter = CategoryAdapter(R.layout.item_category_sub).apply {
            bindToRecyclerView(rvCategory)
            setNewData(categoryList)
            onCheckedListener = {
                checkedPosition = it
                mViewModel.refreshArticleList(categoryList[checkedPosition].id)
            }
        }

        mAdapater = SimpleArticleAdapter(R.layout.item_article_simple).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList(categoryList[checkedPosition].id)
            }, recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = mAdapater.data[position]
                start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }

            setOnItemChildClickListener { _, view, position ->
                val article = mAdapater.data[position]
                if (view.id == R.id.ivCollect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }
                }

            }

        }
        btnReload.setOnClickListener { mViewModel.refreshArticleList(categoryList[checkedPosition].id) }
    }

    override fun scrollToTop() {
        recyclerView?.smoothScrollToPosition(0)
    }

    override fun lazyLoadData() {
        mViewModel.refreshArticleList(categoryList[checkedPosition].id)
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                mAdapater.setNewData(it)
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing=it
            })

            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when (it) {
                    LoadMoreStatus.COMPLETED -> mAdapater.loadMoreComplete()
                    LoadMoreStatus.ERROR -> mAdapater.loadMoreFail()
                    LoadMoreStatus.END -> mAdapater.loadMoreEnd()
                    else -> return@Observer
                }
            })
        }


        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED,viewLifecycleOwner){
            mViewModel.updateListCollectState()
        }

        Bus.observe<Pair<Int,Boolean>>(USER_COLLECT_UPDATED,viewLifecycleOwner){
            mViewModel.updateItemCollectState(it)
        }
    }


    fun check(position:Int){
        if (position!=checkedPosition){
            checkedPosition=position
            categoryAdapter.check(position)
            (rvCategory.layoutManager as LinearLayoutManager)?.scrollToPositionWithOffset(position,8f.toIntPx())
            mViewModel.refreshArticleList(categoryList[checkedPosition].id)
        }
    }
}