package com.bagelly.mvvm.ui.main.system

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system
 * @ClassName: SystemRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午1:49
 */
class SystemRepository {
    suspend fun getArticleCategories()=RetrofitClient.apiService.getArticleCategories().apiData()
}