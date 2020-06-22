package com.bagelly.mvvm.ui.search.result
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.main.home.ArticleAdapter
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManger
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search.history
 * @ClassName: SearchHistoryFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/16 上午11:41
 */
class SearchResultFragment : BaseVmFragment<SearchResultViewModel>() {

    companion object {
        fun newInstance() = SearchResultFragment()
    }
    override fun viewModelClass()=SearchResultViewModel::class.java
    override fun layoutRes() = R.layout.fragment_search_result

    private lateinit var searchResultAdapter: ArticleAdapter

    override fun initView() {
       searchResultAdapter=ArticleAdapter().apply {
           setLoadMoreView(CommonLoadMoreView())
           bindToRecyclerView(recyclerView)
           setOnItemClickListener { _, _, position ->
               val article = data[position]
               ActivityManger.start(
                   DetailActivity::class.java,
                   mapOf(DetailActivity.PARAM_ARTICLE to article)
               )
           }


           setOnItemChildClickListener { _, view, position ->
               val article = data[position]
               if (view.id == R.id.iv_collect && checkLogin()) {
                   view.isSelected = !view.isSelected
                   if (article.collect) {
                       mViewModel.uncollect(article.id)
                   } else {
                       mViewModel.collect(article.id)
                   }
               }
           }

           setOnLoadMoreListener({ mViewModel.loadMore() }, recyclerView)

       }

        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.search() }
        }

        btnReload.setOnClickListener { mViewModel.search() }
    }

    fun doSearch(searchWords: String) {
         mViewModel.search(searchWords)
    }


    override fun observer() {
        super.observer()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer {
                searchResultAdapter.setNewData(it)
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing=it
            })

            emptyStatus.observe(viewLifecycleOwner, Observer {
                emptyView.isVisible=it
            })

            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    LoadMoreStatus.COMPLETED ->searchResultAdapter.loadMoreComplete()
                    LoadMoreStatus.ERROR -> searchResultAdapter.loadMoreFail()
                    LoadMoreStatus.END ->searchResultAdapter.loadMoreEnd()
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