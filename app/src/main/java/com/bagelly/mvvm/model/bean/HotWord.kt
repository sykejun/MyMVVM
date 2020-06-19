package com.bagelly.mvvm.model.bean

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.bean
 * @ClassName: HotWord
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/16 下午1:57
 */
data class HotWord(
    val id: Int,
    val link: String,
    val order: Int,
    val name: String,
    val visible: Int
)