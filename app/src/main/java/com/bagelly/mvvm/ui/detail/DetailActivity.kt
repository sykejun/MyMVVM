package com.bagelly.mvvm.ui.detail

import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ext.htmlToSpanned
import com.bagelly.mvvm.ext.setBrightness
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.store.SettingsStore
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManger
import com.bagelly.mvvm.util.isNightMode
import com.bagelly.mvvm.util.whiteHostList
import com.just.agentweb.*
import kotlinx.android.synthetic.main.activity_detail.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.detail
 * @ClassName: DetailActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 上午9:59
 */
class DetailActivity:BaseVmActivity<DetailViewModel>() {
    companion object{
        const val PARAM_ARTICLE="PARAM_ARTICLE"
    }

    private lateinit var article: Article

    private var agentWeb:AgentWeb?=null

    override fun layoutRes(): Int = R.layout.activity_detail
    override fun viewModelClass(): Class<DetailViewModel> =DetailViewModel::class.java

    override fun initView() {
        article=intent?.getParcelableExtra(PARAM_ARTICLE)?: return
        tvTitle.text=article.title.htmlToSpanned()
        ivBack.setOnClickListener { ActivityManger.finish(DetailActivity::class.java) }
        ivMore.setOnClickListener { ActonFragment.newInstance(article).show(supportFragmentManager) }

        if (isNightMode(this)){
            setBrightness(0.08f)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {
        if (article.id != 0) {
            mViewModel.saveReadHistory(article)
        }
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(webContainer, ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator(getColor(R.color.textColorPrimary), 2)
            .interceptUnkownUrl()
            .setMainFrameErrorView(R.layout.include_reload, R.id.btnReload)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    setTitles(title)
                    super.onReceivedTitle(view, title)
                }

                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    Log.d("WanAandroidWebView", "${consoleMessage?.message()}")
                    return super.onConsoleMessage(consoleMessage)
                }
            })
            .setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return !whiteHostList().contains(request?.url?.host)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    view?.loadUrl(customJs(url))
                }
            })
            .createAgentWeb()
            .ready()
            .get()
        agentWeb?.webCreator?.webView?.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
                textZoom = SettingsStore.getWebTextZoom()
            }
        }
        agentWeb?.urlLoader?.loadUrl(article.link)
    }



    fun changeCollect(){
       if (article.collect){
           mViewModel.uncollect(article.id)
       }else{
           mViewModel.collect(article.id)
       }
    }

    fun refreshPage(){
        agentWeb?.urlLoader?.reload()
    }


    /**
     * 加载js，去掉掘金、简书、CSDN等H5页面的Title、底部操作栏，以及部分广告
     */
    private fun customJs(url: String? = article.link): String {
        val js = StringBuilder()
        js.append("javascript:(function(){")
        when (Uri.parse(url).host) {
            "juejin.im" -> {
                js.append("var headerList = document.getElementsByClassName('main-header-box');")
                js.append("if(headerList&&headerList.length){headerList[0].parentNode.removeChild(headerList[0])}")
                js.append("var openAppList = document.getElementsByClassName('open-in-app');")
                js.append("if(openAppList&&openAppList.length){openAppList[0].parentNode.removeChild(openAppList[0])}")
                js.append("var actionBox = document.getElementsByClassName('action-box');")
                js.append("if(actionBox&&actionBox.length){actionBox[0].parentNode.removeChild(actionBox[0])}")
                js.append("var actionBarList = document.getElementsByClassName('action-bar');")
                js.append("if(actionBarList&&actionBarList.length){actionBarList[0].parentNode.removeChild(actionBarList[0])}")
                js.append("var columnViewList = document.getElementsByClassName('column-view');")
                js.append("if(columnViewList&&columnViewList.length){columnViewList[0].style.margin = '0px'}")
            }
            "www.jianshu.com" -> {
                js.append("var jianshuHeader = document.getElementById('jianshu-header');")
                js.append("if(jianshuHeader){jianshuHeader.parentNode.removeChild(jianshuHeader)}")
                js.append("var headerShimList = document.getElementsByClassName('header-shim');")
                js.append("if(headerShimList&&headerShimList.length){headerShimList[0].parentNode.removeChild(headerShimList[0])}")
                js.append("var fubiaoList = document.getElementsByClassName('fubiao-dialog');")
                js.append("if(fubiaoList&&fubiaoList.length){fubiaoList[0].parentNode.removeChild(fubiaoList[0])}")
                js.append("var ads = document.getElementsByClassName('note-comment-above-ad-wrap');")
                js.append("if(ads&&ads.length){ads[0].parentNode.removeChild(ads[0])}")

                js.append("var lazyShimList = document.getElementsByClassName('v-lazy-shim');")
                js.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[0]){lazyShimList[0].parentNode.removeChild(lazyShimList[0])}")
                js.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[1]){lazyShimList[1].parentNode.removeChild(lazyShimList[1])}")
            }
            "blog.csdn.net" -> {
                js.append("var csdnToolBar = document.getElementById('csdn-toolbar');")
                js.append("if(csdnToolBar){csdnToolBar.parentNode.removeChild(csdnToolBar)}")
                js.append("var csdnMain = document.getElementById('main');")
                js.append("if(csdnMain){csdnMain.style.margin='0px'}")
                js.append("var operate = document.getElementById('operate');")
                js.append("if(operate){operate.parentNode.removeChild(operate)}")
            }
        }
        js.append("})()")
        return js.toString()
    }


    private  fun setTitles(title:String?){
        tvTitle.text=title
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode,event)==true){
            return  true
        }else{
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun observer() {
        super.observer()
        mViewModel.collect.observe(this, Observer {
            if (article.collect!=it){
                article.collect=it
                //收藏状态变化通知刷新
                Bus.post(USER_COLLECT_UPDATED,article.id to it)
            }
        })

        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED,this){
            mViewModel.updateCollectState(article.id)
        }
    }
}