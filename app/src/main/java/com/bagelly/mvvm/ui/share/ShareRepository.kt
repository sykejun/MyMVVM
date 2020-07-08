package com.bagelly.mvvm.ui.share

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.share
 * @ClassName: ShareRespository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/7/8 上午9:16
 */
class ShareRepository {
    suspend fun shareArticle(title:String,link:String)=
        RetrofitClient.apiService.shareArticle(title,link).apiData()
}