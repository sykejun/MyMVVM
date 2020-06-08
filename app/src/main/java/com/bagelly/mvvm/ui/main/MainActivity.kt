package com.bagelly.mvvm.ui.main
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.ext.showToast
import com.bagelly.mvvm.ui.base.BaseActivity
import com.bagelly.mvvm.ui.main.discovery.DiscoveryFragment
import com.bagelly.mvvm.ui.main.home.HomeFragment
import com.bagelly.mvvm.ui.main.mine.MineFragment
import com.bagelly.mvvm.ui.main.navigation.NavigationFragment
import com.bagelly.mvvm.ui.main.system.SystemFragment
import com.google.android.material.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 首页：热门文章、最新项目、广场、项目分类、公众号
 * 体系：体系
 * 发现：Banner、搜索热词、常用网站
 * 导航：导航
 * 我的：登录、注册、积分排行、我的积分、我的分享、稍后阅读、我的收藏、浏览历史、关于作者、开源项目、系统设置
 * 详情：文章详情（收藏、分享、浏览器打开、复制链接、刷新页面）
 * 搜索：搜索历史、热门搜索
 * 设置：日夜间模式、调整亮度、字体大小、清除缓存、检查版本、关于玩安卓、退出登录
 */
class MainActivity : BaseActivity() {
    private lateinit var fragmets: Map<Int, Fragment>
    private var bottomNavigationViewAnimtor: ViewPropertyAnimator? = null
    private var currrentBottomNavigationState = true
    private var previousTimeMillis = 0L

    override fun layoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmets = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.system to createFragment(SystemFragment::class.java),
            R.id.discovery to createFragment(DiscoveryFragment::class.java),
            R.id.navigation to createFragment(NavigationFragment::class.java),
            R.id.mine to createFragment(MineFragment::class.java)
        )

        bottomNavigationView.run {
            setOnNavigationItemSelectedListener { menuItem ->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener { item ->
                val fragment = fragmets.entries.find { it.key == item.itemId }?.value
                if (fragment is ScrollToTop) {
                    fragment.scrollToTop()
                }
            }
        }

        if (savedInstanceState == null) {
            val initialItem = R.id.home
            bottomNavigationView.selectedItemId = initialItem
            showFragment(initialItem)
        }

    }

    private fun showFragment(menuItemId: Int) {
        val currentFragment =
            supportFragmentManager.fragments.find { it.isVisible && it in fragmets.values }
        val targetFragment = fragmets.entries.find { it.key == menuItemId }?.value
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let { if (it.isAdded) show(it) else add(R.id.fl, it) }
        }.commit()


    }

    private fun createFragment(clazz: Class<out Fragment>): Fragment {
        var fragment = supportFragmentManager.fragments.find { it.javaClass == clazz }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.newInstance()
                SystemFragment::class.java -> SystemFragment.newInstance()
                DiscoveryFragment::class.java -> DiscoveryFragment.newInstance()
                NavigationFragment::class.java -> NavigationFragment.newInstance()
                MineFragment::class.java -> MineFragment.newInstance()
                else -> throw  IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }

        return fragment

    }


    override fun onBackPressed() {
        val currentTimMills = System.currentTimeMillis()
        if (currentTimMills - previousTimeMillis < 2000) {
            super.onBackPressed()
        } else
            showToast(R.string.press_again_to_exit)
            previousTimeMillis = currentTimMills
    }


    override fun onDestroy() {
        bottomNavigationViewAnimtor?.cancel()
        bottomNavigationView.clearAnimation()
        bottomNavigationViewAnimtor=null
        super.onDestroy()
    }

    fun animateBottomNavigationView(show:Boolean){
        if (currrentBottomNavigationState==show){
            return
        }
        if (bottomNavigationViewAnimtor != null) {
            bottomNavigationViewAnimtor?.cancel()
            bottomNavigationView.clearAnimation()
        }
        currrentBottomNavigationState=show

        val targetY=if (show)0F else bottomNavigationView.measuredHeight.toFloat()
        val duration=if (show) 225L else 175L
        bottomNavigationViewAnimtor=bottomNavigationView.animate()
            .translationY(targetY)
            .setDuration(duration)
            .setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
            .setListener(object :AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationViewAnimtor=null
                }
            })
    }

}