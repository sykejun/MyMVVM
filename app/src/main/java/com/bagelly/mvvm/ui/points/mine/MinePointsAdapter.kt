package com.bagelly.mvvm.ui.points.mine

import com.bagelly.mvvm.R
import com.bagelly.mvvm.ext.toDateTime
import com.bagelly.mvvm.model.bean.PointRecord
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_mine_points.view.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.points.mine
 * @ClassName: MinePointsAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午4:01
 */
class MinePointsAdapter:BaseQuickAdapter<PointRecord,BaseViewHolder>(R.layout.item_mine_points) {
    override fun convert(helper: BaseViewHolder, item: PointRecord) {
        helper.itemView.run {
            tvReason.text=item.reason
            tvTime.text=item.date.toDateTime("YYYY-MM-dd HH:mm:ss")
            tvPoint.text="+${item.coinCount}"
        }
    }
}