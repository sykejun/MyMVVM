package com.bagelly.mvvm.ui.main.system

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Category
import com.bagelly.mvvm.ui.base.BaseViewModel

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system
 * @ClassName: SystemViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午1:51
 */
class SystemViewModel  :BaseViewModel(){
    private val systemRepository by lazy { SystemRepository() }
    val categories:MutableLiveData<MutableList<Category>> = MutableLiveData()
    val loadingStatus=MutableLiveData<Boolean>()
    val reloadStatus=MutableLiveData<Boolean>()

    fun getArticleCategory(){
        loadingStatus.value=true
        reloadStatus.value=false
        launch(
            block = {
                categories.value=systemRepository.getArticleCategories()
                loadingStatus.value=false
            },error = {
                loadingStatus.value=false
                reloadStatus.value=true
            }
        )
    }
}