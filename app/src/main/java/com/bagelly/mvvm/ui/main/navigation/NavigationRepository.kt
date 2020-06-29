package com.bagelly.mvvm.ui.main.navigation

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.navigation
 * @ClassName: NavigationRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 上午8:59
 */
class NavigationRepository {
    suspend fun getNavigations()=RetrofitClient.apiService.getNavigations().apiData()
}