package com.bagelly.mvvm.ui.main.home

import androidx.core.view.isVisible
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ext.htmlToSpanned
import com.bagelly.mvvm.model.bean.Article
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_article_simple.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home
 * @ClassName: SimpleArticleAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/10 下午1:06
 */
class SimpleArticleAdapter (layoluotResId:Int= R.layout.item_article_simple) :
    BaseQuickAdapter<Article,BaseViewHolder>(layoluotResId) {
    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.run {
            itemView.run {
                tv_author.text=when{
                    !item.author.isNullOrEmpty()->item.author
                    !item.superChapterName.isNullOrEmpty()->item.shareUser

                    else -> context.getString(R.string.anonymous)
                }
                tv_fresh.isVisible=item.fresh
                tv_title.text=item.title.htmlToSpanned()
                tv_time.text=item.niceDate
                iv_collect.isSelected=item.collect
            }

            addOnClickListener(R.id.iv_collect)
        }


    }
}