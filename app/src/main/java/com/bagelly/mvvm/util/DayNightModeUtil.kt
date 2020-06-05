package com.bagelly.mvvm.util

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.util
 * @ClassName: DayNightModeUtil
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 上午10:25
 */

fun isNightMode(context: Context):Boolean{
    val mode=context.resources.configuration.uiMode and UI_MODE_NIGHT_MASK
    return mode==UI_MODE_NIGHT_YES
}

fun setNightMode(isNightMode:Boolean){
    AppCompatDelegate.setDefaultNightMode(
        if (isNightMode) MODE_NIGHT_YES else MODE_NIGHT_NO
    )
}