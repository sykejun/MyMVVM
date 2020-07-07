package com.bagelly.mvvm.ext

import android.app.Activity

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ext
 * @ClassName: ActivityExt
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午2:04
 */

/**
 * 获取Activity的亮度
 * @return 0 ~ 1
 */
fun Activity.getBrightness()=window.attributes.screenBrightness

/**
 * 设置Activity的亮度
 * @param [brightness] 0 ~ 1
 */
fun Activity.setBrightness(brightness:Float){
    val attribute=window.attributes
    attribute.screenBrightness=brightness
    window.attributes=attribute
}

fun Activity.setNavigationBarColor(color:Int){
    window.navigationBarColor=color
}