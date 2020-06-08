package com.bagelly.mvvm.model.bean

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.bean
 * @ClassName: UserInfo
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午2:31
 */

data class UserInfo(
    val admin: Boolean,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String,
    val collectIds: MutableList<Int>,
    val chapterTops: List<Any>
)