package com.bagelly.mvvm.ui.main.system.category

import android.view.LayoutInflater
import android.view.View
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Category
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_system_category_tag.view.*
import java.util.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system.category
 * @ClassName: Item
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 s下午4:05
 */
class ItemTagAdapter(private val categoryList: List<Category>) :TagAdapter<Category>(categoryList){
    override fun getView(parent: FlowLayout?, position: Int, t: Category?): View {
        return LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_system_category_tag,parent,false)
            .apply {
                tvTag.text=categoryList[position].name
            }
    }

}