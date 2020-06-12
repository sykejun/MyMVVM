package com.bagelly.mvvm.ui.main.home

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ext.htmlToSpanned
import com.bagelly.mvvm.ext.toIntPx
import com.bagelly.mvvm.model.bean.Category
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_category_sub.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home
 * @ClassName: CategoryAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/12 上午9:03
 */
class CategoryAdapter(layoutResId:Int= R.layout.item_category_sub):
    BaseQuickAdapter<Category,BaseViewHolder>(layoutResId) {
    private var checkedPostion=0
    var onCheckedListener:((positio:Int)->Unit)?=null
    override fun convert(helper: BaseViewHolder, item: Category) {
           helper.itemView.run {
               ctvCategory.text=item.name.htmlToSpanned()
               ctvCategory.isChecked=checkedPostion==helper.adapterPosition
               setOnClickListener {
                   val position=helper.adapterPosition
                   check(position)
                   onCheckedListener?.invoke(position)
               }

               updateLayoutParams<ViewGroup.MarginLayoutParams> {
                   marginStart=if (helper.adapterPosition==0) 0.8f.toIntPx() else 0f.toIntPx()
               }
           }
    }


    fun check(position: Int){
        checkedPostion=position
        notifyDataSetChanged()
    }
}