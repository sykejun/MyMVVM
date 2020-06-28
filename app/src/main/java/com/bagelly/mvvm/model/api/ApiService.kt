package com.bagelly.mvvm.model.api

import com.bagelly.mvvm.model.bean.*
import retrofit2.http.*

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
    suspend fun getArticleList(@Path("page") page:Int):ApiResult<Pagination<Article>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResult<Any?>
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Int): ApiResult<Any?>

    @GET("/article/listproject/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("/user_article/list/{page}/json")
    suspend fun getUserArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("project/tree/json")
    suspend fun getProjectCategories(): ApiResult<MutableList<Category>>

    @GET("project/list/{page}/json")
    suspend fun getProjectListByCid(@Path("page") page: Int, @Query("cid") cid: Int): ApiResult<Pagination<Article>>
    @GET("wxarticle/chapters/json")
    suspend fun getWechatCategories(): ApiResult<MutableList<Category>>

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWechatArticleList(@Path("page") page: Int, @Path("id") id: Int): ApiResult<Pagination<Article>>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): ApiResult<UserInfo>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String): ApiResult<UserInfo>

    @GET("hotkey/json")
    suspend fun getHotWords(): ApiResult<List<HotWord>>

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun search(@Field("k") keywords: String, @Path("page") page: Int): ApiResult<Pagination<Article>>

    @GET("tree/json")
    suspend fun getArticleCategories(): ApiResult<MutableList<Category>>
    @GET("article/list/{page}/json")
    suspend fun getArticleListByCid(@Path("page") page: Int, @Query("cid") cid: Int): ApiResult<Pagination<Article>>
    @GET("banner/json") suspend fun getBanners(): ApiResult<List<Banner>>

    @GET("friend/json")
    suspend fun getFrequentlyWebsites(): ApiResult<List<Frequently>>
}