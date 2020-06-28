package com.bagelly.mvvm.ui.main.discovery

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Banner
import com.bagelly.mvvm.model.bean.Frequently
import com.bagelly.mvvm.model.bean.HotWord
import com.bagelly.mvvm.ui.base.BaseViewModel

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.discovery
 * @ClassName: DiscoveryViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/28 下午3:53
 */
class DiscoveryViewModel :BaseViewModel() {
    private val discoveryRepository by lazy { DiscoveryRepository() }
    val banners=MutableLiveData<List<Banner>>()
    val hotWords=MutableLiveData<List<HotWord>>()
    val frequentlyList=MutableLiveData<List<Frequently>>()
    val refreshStatus=MutableLiveData<Boolean>()
    val reloadStatus=MutableLiveData<Boolean>()

    fun getData(){
        refreshStatus.value=true
        reloadStatus.value=false

        launch(
            block = {
                banners.value=discoveryRepository.getBanners()
                hotWords.value=discoveryRepository.getHotWords()
                frequentlyList.value=discoveryRepository.getFrequentlyWebsites()
                refreshStatus.value=false
            },
            error = {
               refreshStatus.value=false
                reloadStatus.value=true
            }
        )
    }
}