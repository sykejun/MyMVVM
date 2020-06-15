package com.bagelly.mvvm

import android.app.Application
import com.bagelly.mvvm.common.ActivityLifecycleCallbackAdapter
import com.bagelly.mvvm.model.store.SettingsStore
import com.bagelly.mvvm.util.core.ActivityManger
import com.bagelly.mvvm.util.isMainProcess
import com.bagelly.mvvm.util.setNightMode
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mmkv.MMKV

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm
 * @ClassName: App
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/4 下午5:25
 */
class App :Application() {
    companion object{
        lateinit var instance:App
    }
    override fun onCreate() {
        super.onCreate()
        instance=this

        initLogger()
        //初始化腾讯MMKV.initialize(this)
        MMKV.initialize(this)
        if(isMainProcess(this)){
            init()
        }


    }

    private fun initLogger() {
        val formatStrategy=PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .build()
        Logger.addLogAdapter(object :AndroidLogAdapter(formatStrategy){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
              return BuildConfig.DEBUG
            }
        })
    }

    private fun init() {
        rigesterActivityCallbacks()
        setDayNightMode()
    }

    private fun setDayNightMode() {
        setNightMode(SettingsStore.getNightMode())

    }

    private fun rigesterActivityCallbacks() {
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbackAdapter(
            onActivityCreated = {activity, _ ->
                ActivityManger.activites.add(activity)
            },onActivityDestroyed = {
                activity -> ActivityManger.activites.remove(activity)
            }
        ))


    }
}