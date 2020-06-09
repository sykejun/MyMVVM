package com.bagelly.mvvm.model.api

import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.bean.Paginaton
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.api
 * @ClassName: ApiService
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午3:12
 */

interface ApiService{
    companion object{
        const val BASE_URL = "https://www.wanandroid.com"
    }
    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<Article>>
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page:Int):ApiResult<Paginaton<Article>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResult<Any?>
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Int): ApiResult<Any?>
}