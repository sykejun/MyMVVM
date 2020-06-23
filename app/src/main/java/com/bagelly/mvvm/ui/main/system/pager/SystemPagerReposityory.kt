package com.bagelly.mvvm.ui.main.system.pager

import com.bagelly.mvvm.model.api.RetrofitClient

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system.pager
 * @ClassName: SystemPagerReposityory
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午5:00
 */
class SystemPagerReposityory {
    suspend fun getArticleListByCid(page:Int,cid:Int)= RetrofitClient.apiService.getArticleListByCid(page,cid).apiData()
}