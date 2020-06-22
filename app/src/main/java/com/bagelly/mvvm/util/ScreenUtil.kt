package com.bagelly.mvvm.util

import android.content.Context

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.util
 * @ClassName: ScreenUtil
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午3:07
 */

fun getScreenHeight(context: Context)=context.resources.displayMetrics.heightPixels

fun getScreenWidth(context: Context)=context.resources.displayMetrics.widthPixels

fun getStatusBarHeight(context: Context):Int{
    var statusBarHeight=0
    val resourId=context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourId>0)statusBarHeight=context.resources.getDimensionPixelSize(resourId)

    return statusBarHeight
}