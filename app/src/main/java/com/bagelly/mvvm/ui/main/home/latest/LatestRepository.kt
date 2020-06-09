package com.bagelly.mvvm.ui.main.home.latest

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.latest
 * @ClassName: LatestRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/9 下午2:42
 */

class LatestRepository {
    suspend fun getProjectList(page:Int)=RetrofitClient.apiService.getProjectList(page).apiData()
}