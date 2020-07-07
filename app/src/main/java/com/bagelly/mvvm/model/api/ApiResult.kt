package com.bagelly.mvvm.model.api

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.api
 * @ClassName: ApiResult
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/8 下午2:58
 */

data class ApiResult<T>(val errorCode: Int,val errorMsg:String,private  val data:T){
    fun apiData():T{
        if (errorCode==0) return data else throw ApiException(errorCode,errorMsg)
    }
}