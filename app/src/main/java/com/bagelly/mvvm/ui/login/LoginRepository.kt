package com.bagelly.mvvm.ui.login

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.login
 * @ClassName: LoginRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午4:07
 */

class LoginRepository{
    suspend fun login(username:String,password:String)=RetrofitClient.apiService.login(username,password).apiData()
}