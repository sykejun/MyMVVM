package com.bagelly.mvvm.model.bean

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.bean
 * @ClassName: Frequently
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/28 下午3:47
 */

data class Frequently(
    val icon: String,
    val id: Int,
    val name: String,
    val link: String,
    val order: Int,
    val visible: Int
)