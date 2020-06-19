package com.bagelly.mvvm.ui.search.result

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search.result
 * @ClassName: SearchResultRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/19 下午5:16
 */
class SearchResultRepository {
    suspend fun search(keywords:String,page:Int)= RetrofitClient.apiService.search(keywords,page).apiData()
}