package com.bagelly.mvvm.ui.common

import com.bagelly.mvvm.model.api.RetrofitClient
import com.bagelly.mvvm.model.bean.UserInfo
import com.bagelly.mvvm.model.store.UserInfoStore

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.common
 * @ClassName: UserRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午2:29
 */
open class UserRepository {
    /**
     * 跟新个人信息
     */
    fun updateUserInfo(usreInfo: UserInfo)=UserInfoStore.setUserInfo(usreInfo)

    /**
     * 是否登录的状态
     */
    fun isLogin()=UserInfoStore.isLogin()

    /**
     * 获取个人信息
     */
    fun getUserInfo()=UserInfoStore.getUserInfo()
    /**
     * 清除所有状态
     */

    fun clearLoginState(){
        UserInfoStore.clearUserInfo()
        RetrofitClient.clearCookie()
    }
}