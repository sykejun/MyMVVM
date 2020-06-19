package com.bagelly.mvvm.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ext
 * @ClassName: ViewExt
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/16 下午1:28
 */
/**
 * 弹出软键盘
 */
fun View.showSoftInput(){
    val imm=context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this,InputMethodManager.SHOW_IMPLICIT)
}

/**
 * 隐藏软键盘
  */

fun View.hideSoftInput(){
    val imm=context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken,0)
}
