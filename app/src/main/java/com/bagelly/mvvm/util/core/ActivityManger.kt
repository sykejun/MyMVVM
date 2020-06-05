package com.bagelly.mvvm.util.core

import android.app.Activity
import android.content.Intent
import com.bagelly.mvvm.ext.putExtras

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.util.core
 * @ClassName: ActivityManger
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 上午10:00
 */

object  ActivityManger{
    val activites= mutableListOf<Activity>()

    /**
     *带参数的跳转
     */
    fun start(clazz:Class<out Activity>,params:Map<String,Any> = emptyMap()){
        val currentActivity= activites[activites.lastIndex]
        val intent= Intent(currentActivity,clazz)
        params.forEach{
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    fun finish(vararg clazz: Class<out Activity>){
        activites.forEach {
            activity ->
            if (clazz.contains(activity::class.java)){
                activity.finish()
            }
        }
    }
}