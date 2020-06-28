package com.bagelly.mvvm.ui.main.system
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.common.SimpleFragmentPageAdapter
import com.bagelly.mvvm.model.bean.Category
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.main.MainActivity
import com.bagelly.mvvm.ui.main.system.category.SystemCategoryFragment
import com.bagelly.mvvm.ui.main.system.pager.SystemPagerFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.android.synthetic.main.fragment_system.appBarLayout
import kotlinx.android.synthetic.main.fragment_system.tabLayout
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

class SystemFragment:BaseVmFragment<SystemViewModel>(),ScrollToTop{

    companion object{
        fun newInstance()=SystemFragment()
    }

    private var currentOffset=0
    private val titles= mutableListOf<String>()
    private val fragmments= mutableListOf<SystemPagerFragment>()
    private var cateoryFragment: SystemCategoryFragment?=null

    override fun viewModelClass()=SystemViewModel::class.java
    override fun layoutRes()= R.layout.fragment_system

    override fun initView() {
        reloadView.btnReload.setOnClickListener {
            mViewModel.getArticleCategory()
        }

        ivFilter.setOnClickListener { cateoryFragment?.show(childFragmentManager) }

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener{ _, offset ->
            if (activity is MainActivity &&this.currentOffset!=offset){
                (activity as MainActivity).animateBottomNavigationView(offset>currentOffset)
                currentOffset=offset
            }
        })
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            categories.observe(viewLifecycleOwner, Observer {
                ivFilter.visibility= View.VISIBLE
                tabLayout.visibility=View.VISIBLE
                viewPager.visibility=View.VISIBLE
                setup(it)
                cateoryFragment=SystemCategoryFragment.newInstance(ArrayList(it))
            })

            loadingStatus.observe(viewLifecycleOwner, Observer {
                progressBar.isVisible=it
            })

            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible=it
            })
        }
    }

    override fun initData() {
        mViewModel.getArticleCategory()
    }
    private fun setup(categories: MutableList<Category>) {
        titles.clear()
        fragmments.clear()
        categories.forEach {
            titles.add(it.name)
            fragmments.add(SystemPagerFragment.newInstance(it.children))
        }
        viewPager.adapter=SimpleFragmentPageAdapter(childFragmentManager,fragmments,titles)
        viewPager.offscreenPageLimit=titles.size
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun scrollToTop() {
        if (fragmments.isEmpty()||viewPager==null) return
        fragmments[viewPager.currentItem].scrollToTop()
    }

    fun getCurrentChecked():Pair<Int,Int>{
        if (fragmments.isEmpty()||viewPager==null) return 0 to 0
        val first=viewPager.currentItem
        val second=fragmments[viewPager.currentItem].checkedPosition
        return first to second
    }

    fun check(position:Pair<Int,Int>){
        if (fragmments.isEmpty()||viewPager==null) return
        viewPager.currentItem=position.first
      fragmments[position.first].check(position.second)
    }


}


