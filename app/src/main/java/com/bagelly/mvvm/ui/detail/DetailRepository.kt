package com.bagelly.mvvm.ui.detail

import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.room.RoomHelper

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.detail
 * @ClassName: DetailRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 上午9:42
 */
class DetailRepository {
    suspend fun saveReadHistory(article: Article)=RoomHelper.addReadHistory(article)
}