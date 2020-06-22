package com.bagelly.mvvm.ui.main.system.category

import com.bagelly.mvvm.R
import com.bagelly.mvvm.ext.htmlToSpanned
import com.bagelly.mvvm.model.bean.Category
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_system_category.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system.category
 * @ClassName: SystemCateoryAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午4:12
 */
class SystemCateoryAdapter(
    layoutResId:Int= R.layout.item_system_category,
    categoryList:List<Category>,
    var checked:Pair<Int,Int>
) :BaseQuickAdapter<Category,BaseViewHolder>(layoutResId,categoryList) {
    var onCheckedListener :((checked:Pair<Int,Int>) ->Unit)?=null
    override fun convert(helper: BaseViewHolder, item: Category) {
        helper.itemView.run {
            title.text=item.name.htmlToSpanned()
            tagFlowLayout.adapter=ItemTagAdapter(item.children)
            if (checked.first==helper.adapterPosition){
                tagFlowLayout.adapter.setSelectedList(checked.second)
            }

            tagFlowLayout.setOnTagClickListener { _, position, _ ->
                checked=helper.adapterPosition to position
                notifyDataSetChanged()
                tagFlowLayout.postDelayed({
                    onCheckedListener?.invoke(checked)
                },300)
                true
            }
        }
    }
}