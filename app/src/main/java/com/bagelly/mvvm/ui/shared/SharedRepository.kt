package com.bagelly.mvvm.ui.shared

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.shared
 * @ClassName: SharedRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/30 上午10:17
 */
class SharedRepository {
   suspend fun getSharedArticleList(page:Int)=
       RetrofitClient.apiService.getSharedArticleList(page).apiData()

    suspend fun deletShared(id:Int)=RetrofitClient.apiService.deleteShare(id).apiData()
}