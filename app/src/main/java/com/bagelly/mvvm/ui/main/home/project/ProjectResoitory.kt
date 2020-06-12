package com.bagelly.mvvm.ui.main.home.project

import com.bagelly.mvvm.model.api.RetrofitClient

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
class ProjectResoitory {
    suspend fun getProjectCategories()=RetrofitClient.apiService.getProjectCategories().apiData()
    suspend fun getProjectListCid(page:Int,cid:Int)=RetrofitClient.apiService.getProjectListByCid(page,cid).apiData()
}