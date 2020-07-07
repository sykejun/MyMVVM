package com.bagelly.mvvm.ui.points.rank

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.points.rank
 * @ClassName: PointsRankAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午5:07
 */
class PointsRankRespository {
    suspend fun  getPointsRank(page:Int)=
        RetrofitClient.apiService.getPointsRank(page).apiData()
}