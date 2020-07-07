package com.bagelly.mvvm.ui.opensource

import android.os.Bundle
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.ui.base.BaseActivity
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_open_source.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.opensource
 * @ClassName: OpenSourceActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午3:04
 */
class OpenSourceActivity : BaseActivity() {
    private val openSourceData = listOf(
        Article(
            title = "OkHttp",
            link = "https://github.com/square/okhttp"
        ),
        Article(
            title = "Retrofit",
            link = "https://github.com/square/retrofit"
        ),
        Article(
            title = "BaseRecyclerViewAdapterHelper",
            link = "https://github.com/CymChad/BaseRecyclerViewAdapterHelper"
        ),
        Article(
            title = "FlowLayout",
            link = "https://github.com/hongyangAndroid/FlowLayout"
        ),
        Article(
            title = "Banner",
            link = "https://github.com/youth5201314/banner"
        ),
        Article(
            title = "Glide",
            link = "https://github.com/bumptech/glide"
        ),
        Article(
            title = "Glide-Tansformations",
            link = "https://github.com/wasabeef/glide-transformations"
        ),
        Article(
            title = "AgentWeb",
            link = "https://github.com/Justson/AgentWeb"
        ),
        Article(
            title = "LiveEventBus",
            link = "https://github.com/JeremyLiao/LiveEventBus"
        ),
        Article(
            title = "PersistentCookieJar",
            link = "https://github.com/franmontiel/PersistentCookieJar"
        )
    )

    override fun layoutRes() = R.layout.activity_open_source

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OpenSourceAdapter().apply {
            bindToRecyclerView(recyclerView)
            setNewData(openSourceData)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }
        }

        ivBack.setOnClickListener { ActivityManager.finish(OpenSourceActivity::class.java) }
    }
}