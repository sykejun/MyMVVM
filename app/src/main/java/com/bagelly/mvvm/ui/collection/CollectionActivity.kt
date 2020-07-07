package com.bagelly.mvvm.ui.collection
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.loadmore.CommonLoadMoreView
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.ui.common.loadmore.LoadMoreStatus
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.main.home.ArticleAdapter
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.include_reload.*
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.collection
 * @ClassName: CollectionActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午1:23
 */
class CollectionActivity :BaseVmActivity<CollectionViewModel>(){
    private lateinit var mAdapter:ArticleAdapter
    override fun viewModelClass()=CollectionViewModel::class.java
    override fun layoutRes()= R.layout.activity_collection
    override fun initView() {
        mAdapter=ArticleAdapter().apply {
            setLoadMoreView(CommonLoadMoreView())
            bindToRecyclerView(recyclerView)
            setOnItemClickListener { _, _, position ->
                val article=data[position]
                ActivityManager.start(
                    DetailActivity::class.java, mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }

            setOnItemChildClickListener { _, view, position ->
                val article = data[position]
                if (view.id==R.id.iv_collect){
                    mViewModel.uncollect(article.id)
                    removeItem(position)
                }
            }

            setOnLoadMoreListener({ mViewModel.loadMore() },recyclerView)
        }

        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.refresh() }
        }

        btnReload.setOnClickListener { mViewModel.refresh() }

        ivBack.setOnClickListener { finish() }
    }

    private fun removeItem(position: Int) {
        mAdapter.remove(position)
        emptyView.isVisible=mAdapter.data.isEmpty()
    }

    override fun initData() {
        mViewModel.refresh()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {


            articleList.observe(this@CollectionActivity, Observer {
                mAdapter.setNewData(it)
            })

            refreshStatus.observe(this@CollectionActivity, Observer {
                swipeRefreshLayout.isRefreshing=it
            })


             loadMoreStatus.observe(this@CollectionActivity, Observer {
                 when(it){
                     LoadMoreStatus.ERROR ->mAdapter.loadMoreFail()
                     LoadMoreStatus.COMPLETED ->mAdapter.loadMoreComplete()
                     LoadMoreStatus.END ->mAdapter.loadMoreEnd()
                     else -> return@Observer
                 }
             })

            reloadStatus.observe(this@CollectionActivity, Observer {
                reloadView.isVisible=it
            })
        }

        Bus.observe<Pair<Int,Boolean>>(USER_COLLECT_UPDATED,this){(id,collect) ->
            if (collect){
                mViewModel.refresh()
            }else{
                val position=mAdapter.data.indexOfFirst { it.originId==id }
                if (position!=-1){
                    removeItem(position)
                }
            }
        }
    }

}