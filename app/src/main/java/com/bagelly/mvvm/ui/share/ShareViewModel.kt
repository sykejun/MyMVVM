package com.bagelly.mvvm.ui.share

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.UserInfo
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.UserRepository
import com.bagelly.mvvm.util.share

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.share
 * @ClassName: ShareViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/7/8 上午9:28
 */

class ShareViewModel:BaseViewModel(){
    private val shareRepository by lazy { ShareRepository()}

    val userInfo=MutableLiveData<UserInfo>()
    val submitting=MutableLiveData<Boolean>()
    val shareResult=MutableLiveData<Boolean>()

    fun getUserInfo(){
        userInfo.value=userRepository.getUserInfo()
    }

    fun shareArticle(title:String,link:String){
        submitting.value=true
        launch(
            block = {
                shareRepository.shareArticle(title,link)
                shareResult.value=true
                submitting.value=false
            },error = {
                shareResult.value=false
                submitting.value=false
            }
        )
    }
}