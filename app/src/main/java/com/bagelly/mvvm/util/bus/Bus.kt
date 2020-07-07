package com.bagelly.mvvm.util.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.util.bus
 * @ClassName: Bus
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午3:55
 */
object Bus {

    inline  fun <reified T> post(channel:String,value: T)=
        LiveEventBus.get(channel,T::class.java).post(value)


    inline  fun <reified  T> observe(
        channel: String,
        owner:LifecycleOwner,
        crossinline  observer: ((value:T) ->Unit)
    )=LiveEventBus.get(channel,T::class.java).observe(owner, Observer{
        observer(it)
    })
}