package com.bagelly.mvvm.util

import android.app.ActivityManager
import android.content.Context
import android.os.Process

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.util
 * @ClassName: ProcessHelper
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/4 下午5:46
 */
fun  isMainProcess(context: Context)=context.packageName==currenProcessName(context)

fun currenProcessName(context: Context): String {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    manager.runningAppProcesses.forEach {
        if (it.pid== Process.myPid()) return it.processName
    }
    return  ""
}
