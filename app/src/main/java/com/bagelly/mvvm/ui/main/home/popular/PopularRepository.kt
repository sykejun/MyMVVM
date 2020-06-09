package com.bagelly.mvvm.ui.main.home.popular

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.popular
 * @ClassName: PopularRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/8 下午2:55
 */
class PopularRepository {
    suspend fun getTopArticleList()=RetrofitClient.apiService.getTopArticleList().apiData()
    suspend fun getArticleList(page:Int)=RetrofitClient.apiService.getArticleList(page).apiData()
}