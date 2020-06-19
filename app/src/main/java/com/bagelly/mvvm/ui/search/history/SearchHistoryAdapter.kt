package com.bagelly.mvvm.ui.search.history

import com.bagelly.mvvm.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_search_history.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search.history
 * @ClassName: SearchHistoryAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/19 下午2:53
 */
class SearchHistoryAdapter(private var layoutResId:Int= R.layout.item_search_history):BaseQuickAdapter<String,BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.run {itemView.run {
            tvLabel.text = item
        }
            addOnClickListener(R.id.ivDelete)

        }
    }
}