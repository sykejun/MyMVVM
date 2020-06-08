package com.bagelly.mvvm.ui.splash

import android.os.Bundle
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ui.base.BaseActivity
import com.bagelly.mvvm.ui.main.MainActivity
import com.bagelly.mvvm.util.core.ActivityManger

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.splash
 * @ClassName: SplashActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/8 上午8:48
 */
class SplashActivity :BaseActivity(){
    override fun layoutRes(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.postDelayed({
            ActivityManger.start(MainActivity::class.java)
            ActivityManger.finish(SplashActivity::class.java)
        },1000)
    }
}