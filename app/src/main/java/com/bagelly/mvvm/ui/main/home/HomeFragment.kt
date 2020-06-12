package com.bagelly.mvvm.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.common.SimpleFragmentPageAdapter
import com.bagelly.mvvm.ui.base.BaseFragment
import com.bagelly.mvvm.ui.main.MainActivity
import com.bagelly.mvvm.ui.main.home.latest.LatestFragment
import com.bagelly.mvvm.ui.main.home.plaza.PlazaFragment
import com.bagelly.mvvm.ui.main.home.popular.PopularFragment
import com.bagelly.mvvm.ui.main.home.project.ProjectFragment
import com.bagelly.mvvm.ui.main.home.wechat.WechatFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_home.*

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

class HomeFragment:BaseFragment(),ScrollToTop{
    private  lateinit var fragments:List<Fragment>
    private var currentOffset=0


    companion object{
        fun newInstance()=HomeFragment()
    }

    override fun layoutRes(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragments= listOf(
            PopularFragment.newInstance(),
            LatestFragment.newInstance(),
            PlazaFragment.newInstance(),
            ProjectFragment.newInstance(),
            WechatFragment.newInstance()
        )

        val titles= listOf<String>(
            getString(R.string.popular_articles),
            getString(R.string.latest_project),
            getString(R.string.plaza),
            getString(R.string.project_category),
            getString(R.string.wechat_public)

        )

        viewpager.adapter=SimpleFragmentPageAdapter(childFragmentManager,fragments,titles)
        viewpager.offscreenPageLimit=fragments.size
        tabLayout.setupWithViewPager(viewpager)

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && this.currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })

        llSearch.setOnClickListener {
            // TODO: 2020/6/10 去搜索页面
        }
    }

    override fun scrollToTop() {
        if (!this::fragments.isInitialized) return
        val currentFragment=fragments[viewpager.currentItem]
        if (currentFragment is ScrollToTop&&currentFragment.isVisible){
            currentFragment.scrollToTop()
        }
    }
}