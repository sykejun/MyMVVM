package com.bagelly.mvvm.ui.main.discovery

import android.view.LayoutInflater
import android.view.View
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Frequently
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_nav_tag.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.discovery
 * @ClassName: TagAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/28 下午5:02
 */
class TagAdapter(private val frequentlyList:List<Frequently>):TagAdapter<Frequently>(frequentlyList) {
    override fun getView(parent: FlowLayout?, position: Int, t: Frequently?): View {
        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_nav_tag,parent,false)
            .apply {
                tvTag.text=frequentlyList[position].name
            }
    }
}