package com.bagelly.mvvm.ui.common

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.common
 * @ClassName: CollectRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/8 下午3:16
 */
class CollectRepository {
    suspend fun collect(id:Int)=RetrofitClient.apiService.collect(id).apiData()
    suspend fun uncollect(id:Int)=RetrofitClient.apiService.uncollect(id).apiData()
}