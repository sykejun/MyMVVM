package com.bagelly.mvvm.ext

import com.bagelly.mvvm.App
import com.bagelly.mvvm.util.dpToPx
import com.bagelly.mvvm.util.pxToDp

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ext
 * @ClassName: FloatExt
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/12 上午10:22
 */
fun Float.toPx()=dpToPx(App.instance,this)

fun Float.toIntPx()= dpToPx(App.instance,this).toInt()

fun Float.toDp()= pxToDp(App.instance,this)
