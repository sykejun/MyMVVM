package com.bagelly.mvvm.ui.main.discovery
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.ScrollToTop
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.bean.Banner
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.detail.DetailActivity.Companion.PARAM_ARTICLE
import com.bagelly.mvvm.ui.main.MainActivity
import com.bagelly.mvvm.ui.search.SearchActivity
import com.bagelly.mvvm.ui.share.ShareActivity
import com.bagelly.mvvm.util.core.ActivityManager
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_discovery.*
import kotlinx.android.synthetic.main.include_reload.*

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

class DiscoveryFragment:BaseVmFragment<DiscoveryViewModel>(),ScrollToTop{

    private lateinit var hotWordsAdapter: HotWordsAdapter
    companion object{
        fun newInstance()=DiscoveryFragment()
    }

    override fun layoutRes()= R.layout.fragment_discovery
    override fun viewModelClass()=DiscoveryViewModel::class.java

    override fun initView() {
        ivAdd.setOnClickListener { checkLogin{ActivityManager.start(ShareActivity::class.java)} }
        ivSearch.setOnClickListener { ActivityManager.start(SearchActivity::class.java) }
        swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.getData() }
        }

        hotWordsAdapter=HotWordsAdapter(R.layout.item_hot_word).apply {
            bindToRecyclerView(rvHotWord)
        }

        btnReload.setOnClickListener {
            mViewModel.getData()
        }

        nestedScollView.setOnScrollChangeListener{v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (activity is MainActivity&&scrollY!=oldScrollY){
                (activity as MainActivity).animateBottomNavigationView(scrollY<oldScrollY)
            }
        }
    }
    override fun scrollToTop() {
        nestedScollView?.smoothScrollTo(0, 0)
    }

    override fun initData() {
        mViewModel.getData()
    }

    override fun onResume() {
        super.onResume()
        bannerView.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        bannerView.stopAutoPlay()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            banners.observe(viewLifecycleOwner, Observer {
                setupBanner(it)
            })

            hotWords.observe(viewLifecycleOwner, Observer {
                hotWordsAdapter.setNewData(it)
                tvHotWordTitle.isVisible = it.isNotEmpty()
            })

            frequentlyList.observe(viewLifecycleOwner, Observer {
                tagFlowLayout.adapter=TagAdapter(it)
                tagFlowLayout.setOnTagClickListener { view, position, parent ->
                    val frequently=it[position]
                    ActivityManager.start(
                        DetailActivity::class.java,
                        mapOf(
                            PARAM_ARTICLE to Article(
                                title = frequently.name,
                                link = frequently.link
                            )
                        )
                    )
                    false
                }

                tvFrquently.isGone=it.isEmpty()

            })

            refreshStatus.observe(viewLifecycleOwner, Observer {
                swipeRefreshLayout.isRefreshing=it
            })

            reloadStatus.observe(viewLifecycleOwner, Observer {
                reloadView.isVisible=it
            })
        }
    }

    private fun setupBanner(banners: List<Banner>) {
        bannerView.run {
            setBannerStyle(BannerConfig.NOT_INDICATOR)
            setImageLoader(BannerImageLoader())
            setImages(banners)
            setBannerAnimation(Transformer.BackgroundToForeground)
            start()
            setOnBannerListener {
               val banner=banners[it]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(PARAM_ARTICLE to Article(title = banner.title, link = banner.url))
                )
            }
        }
    }
}