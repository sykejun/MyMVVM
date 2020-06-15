package com.bagelly.mvvm.ui.login

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.login
 * @ClassName: LoginViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午3:35
 */
class LoginViewModel :BaseViewModel() {
    private  val loginRepository by lazy {LoginRepository() }
    val submitting=MutableLiveData<Boolean>()
    val loginResult=MutableLiveData<Boolean>()

    fun login(account:String,password:String){
        submitting.value=true
        launch(
            block = {
                val userInfo=loginRepository.login(account,password)
                userRepository.updateUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED,true)
                submitting.value=false
                loginResult.value=true
            },error = {
                submitting.value=false
                loginResult.value=false
            }
        )
    }
}