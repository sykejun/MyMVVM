package com.bagelly.mvvm.ui.main.discovery

import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.HotWord
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_hot_word.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.discovery
 * @ClassName: HotWordsAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/28 下午4:29
 */
class HotWordsAdapter(layoutResId:Int= R.layout.item_hot_word):
    BaseQuickAdapter<HotWord,BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: HotWord) {
      helper.itemView.tvName.text=item.name
    }
}