package com.bagelly.mvvm.model.bean

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.bean
 * @ClassName: Navigation
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 上午9:03
 */
data class Navigation (
    val cid: Int,
    val name: String,
    val articles: List<Article>
)