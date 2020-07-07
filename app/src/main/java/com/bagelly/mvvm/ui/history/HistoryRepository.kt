package com.bagelly.mvvm.ui.history

import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.room.RoomHelper

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.history
 * @ClassName: HistoryRepository
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/7/7 上午9:20
 */
class HistoryRepository {
    suspend fun getReadHistroy()=RoomHelper.queryAllReadHistory()
    suspend fun deleteHistory(article: Article)=RoomHelper.deleteReadHistory(article)

}