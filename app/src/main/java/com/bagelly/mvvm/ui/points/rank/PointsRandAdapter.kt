package com.bagelly.mvvm.ui.points.rank
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.PointRank
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_points_rank.view.*
/**
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.points.rank
 * @ClassName: PointsRandAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/30 上午9:08
 */
class PointsRandAdapter :BaseQuickAdapter<PointRank,BaseViewHolder>(R.layout.item_points_rank) {
    override fun convert(helper: BaseViewHolder, item: PointRank) {
        helper.itemView.run {
            tvNo.text="${helper.adapterPosition+1}"
            tvName.text=item.username
            tvPoints.text=item.coinCount.toString()
        }
    }
}