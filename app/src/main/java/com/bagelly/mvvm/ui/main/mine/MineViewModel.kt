package com.bagelly.mvvm.ui.main.mine

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.UserInfo
import com.bagelly.mvvm.ui.base.BaseViewModel

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.mine
 * @ClassName: MineViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 上午11:19
 */
class MineViewModel:BaseViewModel() {
    val userInfo= MutableLiveData<UserInfo?>()
    val isLogin= MutableLiveData<Boolean>()

    fun getUserInfo(){
        isLogin.value=userRepository.isLogin()
        userInfo.value=userRepository.getUserInfo()
    }
}