package com.bagelly.mvvm.model.api

import com.bagelly.mvvm.App
import com.bagelly.mvvm.model.api.ApiService.Companion.BASE_URL
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.api
 * @ClassName: RetrofitClient
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午3:05
 */
object RetrofitClient {
    //登录的Cookie处理
    private  val cookieJar=PersistentCookieJar(SetCookieCache(),
        SharedPrefsCookiePersistor(App.instance)
    )
    //日志的拦截器
    private val logInterceptor=LogInterceptor()

    private  val okHttpClient=OkHttpClient.Builder()
        .callTimeout(10,TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .addInterceptor(logInterceptor)
        .build()

    private val retrofit=Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService:ApiService= retrofit.create(ApiService::class.java)

    fun clearCookie()= cookieJar.clear()
}