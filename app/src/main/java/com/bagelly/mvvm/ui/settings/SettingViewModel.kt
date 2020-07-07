package com.bagelly.mvvm.ui.settings

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.settings
 * @ClassName: SettingViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/7/7 下午3:49
 */
class SettingViewModel: BaseViewModel() {
    val isLogin=MutableLiveData<Boolean>()
    fun getLoginStatus(){
        isLogin.value=userRepository.isLogin()
    }

    fun logout(){
        userRepository.clearLoginState()
        Bus.post(USER_LOGIN_STATE_CHANGED,false)
    }
}