package com.bagelly.mvvm.ui.register

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.register
 * @ClassName: RegiserRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午5:24
 */
class RegiserRepository {
    suspend fun  register(username:String,password:String,repassword:String)=
        RetrofitClient.apiService.register(username,password,repassword).apiData()
}