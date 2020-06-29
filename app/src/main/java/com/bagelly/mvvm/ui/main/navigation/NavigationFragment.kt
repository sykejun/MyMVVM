package com.bagelly.mvvm.ui.main.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.main.MainActivity
import com.bagelly.mvvm.util.core.ActivityManger
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.include_reload.view.*
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home
 * @ClassName: HomeFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/8 上午11:12
 */

class NavigationFragment:BaseVmFragment<NavigationViewModel>(),ScrollToTop{
    private lateinit var mAdapter: NavigationAdapter
    private var currentPosition=0

    companion object{
        fun newInstance()=NavigationFragment()
    }
    override fun viewModelClass()=NavigationViewModel::class.java

    override fun layoutRes()= R.layout.fragment_navigation

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.getNavigations()
            }

            mAdapter=NavigationAdapter(R.layout.item_navigation).apply {
                bindToRecyclerView(recyclerView)
                onItemTagClickListener={
                    ActivityManger.start(
                        DetailActivity::class.java,
                        mapOf(DetailActivity.PARAM_ARTICLE to Article(title = it.title, link = it.link))
                    )
                }
            }

            btnReload.setOnClickListener {
                mViewModel.getNavigations()
            }

            recyclerView.setOnScrollChangeListener{v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (activity is MainActivity &&scrollY!=oldScrollY){
                    (activity as MainActivity).animateBottomNavigationView(scrollY<oldScrollY)
                }

                if (scrollY<oldScrollY){
                    tvFloatTitle.text=mAdapter.data[currentPosition].name
                }

                val lm=recyclerView.layoutManager as LinearLayoutManager
                val nextView=lm.findViewByPosition(currentPosition+1)
                if (nextView!=null){
                     tvFloatTitle.y=if (nextView.top<tvFloatTitle.measuredHeight) (nextView.top-tvFloatTitle.measuredHeight).toFloat() else 0F
            }
               currentPosition=lm.findFirstVisibleItemPosition()
                if (scrollY>oldScrollY) tvFloatTitle.text=mAdapter.data[currentPosition].name

            }
        }
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            navigatons.observe(viewLifecycleOwner, Observer {
                tvFloatTitle.isGone=it.isEmpty()
                tvFloatTitle.text=it[0].name
                mAdapter.setNewData(it)
            })

            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible=it
            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing=it
            })
        }
    }

    override fun scrollToTop() {
        recyclerView?.smoothScrollToPosition(0)
    }

    override fun initData() {
        mViewModel.getNavigations()
    }
}