package com.bagelly.mvvm.ui.main.home.wechat

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.main.home.ArticleAdapter
import com.bagelly.mvvm.ui.main.home.CategoryAdapter
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.fragment_popular.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.include_reload.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.project
 * @ClassName: ProjectFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/12 上午8:40
 */
class WechatFragment:BaseVmFragment<WechatViewModel> (),ScrollToTop{
    companion object{
        fun newInstance()=WechatFragment()
    }

    private  lateinit var  mAdapter:ArticleAdapter
    private lateinit var mCategoryAdapter: CategoryAdapter
    override fun viewModelClass()=WechatViewModel::class.java

    override fun layoutRes(): Int = R.layout.fragment_wechat

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refreshProjectList() }
        }

        mCategoryAdapter=CategoryAdapter(R.layout.item_category_sub)
            .apply {
                bindToRecyclerView(rvCategory)
                onCheckedListener={
                    mViewModel.refreshProjectList(it)
                }
            }

        mAdapter=ArticleAdapter(R.layout.item_article).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener({
                mViewModel.loadMorePorjectList()
            },recyclerView)
            setOnItemClickListener { _, _, position ->
                val article =mAdapter.data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }

            setOnItemChildClickListener { _, view, position ->
              val article = mAdapter.data[position]
                if (view.id==R.id.iv_collect&&checkLogin()){
                    view.isSelected=!view.isSelected
                    if (article.collect){
                        mViewModel.uncollect(article.id)
                    }else{
                        mViewModel.collect(article.id)
                    }
                }
            }

        }

        reloadListView.btnReload.setOnClickListener {
            mViewModel.refreshProjectList()
        }

        reloadView.btnReload.setOnClickListener {
            mViewModel.getProjectCategory()
        }

    }
    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    override fun lazyLoadData() {
        mViewModel.getProjectCategory()
        }

    override fun observer() {
        super.observer()
        mViewModel.run {
            cateories.observe(viewLifecycleOwner, Observer {
                rvCategory.isGone=it.isEmpty()
                mCategoryAdapter.setNewData(it)
            })

            checkedCategory.observe(viewLifecycleOwner, Observer {
                mCategoryAdapter.check(it)
            })

            articleList.observe(viewLifecycleOwner, Observer {
                mAdapter.setNewData(it)
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing=it
            })

            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    LoadMoreStatus.COMPLETED ->mAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR   ->mAdapter.loadMoreFail()
                    LoadMoreStatus.END ->mAdapter.loadMoreEnd()
                    else -> return@Observer
                }
            })


            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible=it
            })

            reloadListStatus.observe(viewLifecycleOwner, Observer {
                reloadListView.isVisible=it
            })

            Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED,viewLifecycleOwner){
                mViewModel.undateListCollectState()
            }

            Bus.observe<Pair<Int,Boolean>>(USER_COLLECT_UPDATED,viewLifecycleOwner){
                mViewModel.updateItemCollectState(it)
            }
        }
    }
}