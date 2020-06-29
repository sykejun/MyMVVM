package com.bagelly.mvvm.ui.main.navigation

import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.bean.Navigation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_navigation.view.*
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.navigation
 * @ClassName: NavigationAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 上午10:28
 */
class NavigationAdapter(layoutResInt: Int= R.layout.item_navigation):BaseQuickAdapter<Navigation,BaseViewHolder>(layoutResInt) {
   var onItemTagClickListener:((article:Article) ->Unit)?=null
    override fun convert(helper: BaseViewHolder, item: Navigation) {
        helper.itemView.run {
            title.text=item.name
            tagFlawLayout.adapter=ItmeTagAdapter(item.articles)
            tagFlawLayout.setOnTagClickListener { _, position, _ ->
                onItemTagClickListener?.invoke(item.articles[position])
                true
            }

        }

    }
}