package com.bagelly.mvvm.ui.main.navigation

import android.view.LayoutInflater
import android.view.View
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Article
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_nav_tag.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.navigation
 * @ClassName: ItmeTagAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 上午10:00
 */
class ItmeTagAdapter(private val articles:List<Article> ):TagAdapter<Article>(articles) {
    override fun getView(parent: FlowLayout?, position: Int, t: Article?): View {
        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_nav_tag,parent,false).apply {
                tvTag.text=articles[position].title
            }
    }
}