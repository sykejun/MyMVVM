package com.bagelly.mvvm.ui.main.home.plaza

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.plaza
 * @ClassName: PlazaRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/10 上午10:02
 */
class PlazaRepository {
    suspend fun getUserArticleList(page:Int)=RetrofitClient.apiService.getUserArticleList(page).apiData()
}