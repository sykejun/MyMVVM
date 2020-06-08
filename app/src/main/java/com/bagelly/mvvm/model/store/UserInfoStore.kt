package com.bagelly.mvvm.model.store

import com.bagelly.mvvm.model.bean.UserInfo
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.store
 * @ClassName: UserInfoStore
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午2:34
 */

object UserInfoStore {
    private  const val   KEY_USER_INFO="KEY_USER_INFO"
    private  val mmkv by lazy{ MMKV.defaultMMKV() }
    private  val gson by lazy { Gson() }

    fun isLogin():Boolean{
        val userInfoStr=   mmkv.decodeString(KEY_USER_INFO,"")
        return userInfoStr.isNotEmpty()

    }

    fun setUserInfo(userInfo: UserInfo){
        mmkv.encode(KEY_USER_INFO,gson.toJson(userInfo))
    }

    fun getUserInfo():UserInfo?{
     val userInfoStr=   mmkv.decodeString(KEY_USER_INFO,"")
        return   if (userInfoStr.isNotEmpty()){
            gson.fromJson(userInfoStr,UserInfo::class.java)
        }else{
            null
        }
    }

    fun clearUserInfo(){
        mmkv.reKey(KEY_USER_INFO)
    }
}