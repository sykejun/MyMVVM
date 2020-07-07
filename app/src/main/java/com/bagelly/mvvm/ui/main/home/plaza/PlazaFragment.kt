package com.bagelly.mvvm.ui.main.home.plaza
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.main.home.SimpleArticleAdapter
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.include_reload.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.plaza
 * @ClassName: PlazaFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/10 下午1:00
 */
class PlazaFragment:BaseVmFragment<PlazaViewModel>(),ScrollToTop {

    companion object{ fun newInstance()=PlazaFragment()}

    private lateinit  var mAdapterSimple : SimpleArticleAdapter

    override fun layoutRes(): Int = R.layout.fragment_plaza

    override fun viewModelClass(): Class<PlazaViewModel> =PlazaViewModel::class.java
    override fun scrollToTop() {
       recyclerview.smoothScrollToPosition(0)
    }

    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.refreshArticleList()
            }
        }

        mAdapterSimple=SimpleArticleAdapter(R.layout.item_article_simple).apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerview)
            setOnLoadMoreListener({
                mViewModel.loadMoreArticleList()
            },recyclerview)

            setOnItemClickListener{_, view, position ->
                val article = mAdapterSimple.data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }

            setOnItemChildClickListener { _, view, position ->
                val article = mAdapterSimple.data[position]
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

        btnReload.setOnClickListener {
            mViewModel.refreshArticleList()
        }
    }

    override fun lazyLoadData() {
        mViewModel.refreshArticleList()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            articleList.observe(viewLifecycleOwner, Observer { mAdapterSimple.setNewData(it) })
            refreshStatus.observe(viewLifecycleOwner, Observer { swipeRefreshLayout.isRefreshing=it })
            loadMoreStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    LoadMoreStatus.COMPLETED ->mAdapterSimple.loadMoreComplete()
                    LoadMoreStatus.ERROR ->mAdapterSimple.loadMoreFail()
                    LoadMoreStatus.END ->mAdapterSimple.loadMoreEnd()
                    else ->return@Observer
                }
            })

            reloadStatus.observe(viewLifecycleOwner, Observer { reloadView.isVisible=it })
        }

       Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED,viewLifecycleOwner){mViewModel.updateListCollectState()}
       Bus.observe<Pair<Int,Boolean>>(USER_COLLECT_UPDATED,viewLifecycleOwner){ mViewModel.updateItemCollectState(it)}
    }
}