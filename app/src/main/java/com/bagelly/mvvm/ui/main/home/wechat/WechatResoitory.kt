package com.bagelly.mvvm.ui.main.home.wechat

import com.bagelly.mvvm.model.api.RetrofitClient
import java.util.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home.project
 * @ClassName: ProjectResoitory
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/11 上午9:35
 */
class WechatResoitory {
    suspend fun getWechatCategories()=RetrofitClient.apiService.getWechatCategories().apiData()
    suspend fun getWechatArticleList(page:Int,cid:Int)=RetrofitClient.apiService.getWechatArticleList(page,cid).apiData()
}