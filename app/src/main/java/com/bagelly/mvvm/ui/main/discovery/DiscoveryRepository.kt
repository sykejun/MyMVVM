package com.bagelly.mvvm.ui.main.discovery

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.discovery
 * @ClassName: DiscoveryRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/24 上午10:14
 */
class DiscoveryRepository {
    suspend fun getBanners()=RetrofitClient.apiService.getBanners().apiData()
    suspend fun getHotWords()=RetrofitClient.apiService.getHotWords().apiData()
    suspend fun getFrequentlyWebsites()=RetrofitClient.apiService.getFrequentlyWebsites().apiData()
}