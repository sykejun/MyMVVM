package com.bagelly.mvvm.model.bean

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.bean
 * @ClassName: Paginaton
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/8 下午3:02
 */

data class Pagination<T>(
    val offset: Int,
    val size: Int,
    val total: Int,
    val pageCount: Int,
    val curPage: Int,
    val over: Boolean,
    val datas: List<T>
)