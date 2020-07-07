package com.bagelly.mvvm.ui.opensource

import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Article
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.activity_detail.view.tvTitle
import kotlinx.android.synthetic.main.item_mine_points.view.*
import kotlinx.android.synthetic.main.item_open_source.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.opensource
 * @ClassName: OpenSourceAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/7/7 上午11:14
 */
class OpenSourceAdapter:BaseQuickAdapter<Article,BaseViewHolder>(R.layout.item_open_source) {
    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.itemView.run {
            tvTitle.text=item.title
            tvLink.text=item.link
        }
    }
}