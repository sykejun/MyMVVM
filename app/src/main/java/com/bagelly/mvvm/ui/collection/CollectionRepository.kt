package com.bagelly.mvvm.ui.collection

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.collection
 * @ClassName: CollectionRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/30 下午4:20
 */
class CollectionRepository {
    suspend fun getCollectionList(page: Int)=
        RetrofitClient.apiService.getCollectionList(page).apiData()
}