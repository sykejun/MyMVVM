package com.bagelly.mvvm.ui.register

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.register
 * @ClassName: RegisterViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午5:23
 */
class RegisterViewModel :BaseViewModel() {
    private val regiserRepository by lazy { RegiserRepository() }

    val submitting=MutableLiveData<Boolean>()
    val registerResult=MutableLiveData<Boolean>()

    fun register(account:String,password:String,confirmPassword:String){
        submitting.value=true
        launch(
            block = {
                val userInfo=regiserRepository.register(account,password,confirmPassword)
                userRepository.updateUserInfo(userInfo)
                Bus.post(USER_LOGIN_STATE_CHANGED,true)
                registerResult.value=true
                submitting.value=false


            },error = {
                registerResult.value=false
                submitting.value=false

            }
        )
    }

}