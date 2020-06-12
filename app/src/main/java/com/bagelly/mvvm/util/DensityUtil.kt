package com.bagelly.mvvm.util

import android.content.Context

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.util
 * @ClassName: DensityUtil
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/12 上午10:24
 */

fun dpToPx(context: Context,dp:Float):Float{
    return dp*context.resources.displayMetrics.density
}

fun pxToDp(context: Context,px:Float):Float{
    return px/context.resources.displayMetrics.density
}
