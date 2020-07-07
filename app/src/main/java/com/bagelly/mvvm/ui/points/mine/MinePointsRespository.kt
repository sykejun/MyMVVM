package com.bagelly.mvvm.ui.points.mine

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.points.mine
 * @ClassName: MinePointsRespository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午3:38
 */
class MinePointsRespository {
    suspend fun getMyPoints()=RetrofitClient.apiService.getPoints().apiData()
    suspend fun getPointsRecord(page:Int)=
        RetrofitClient.apiService.getPointsRecord(page).apiData()
}