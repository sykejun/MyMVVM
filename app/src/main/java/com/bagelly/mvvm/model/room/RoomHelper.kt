package com.bagelly.mvvm.model.room

import androidx.room.Room
import com.bagelly.mvvm.App
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.bean.Tag

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.room
 * @ClassName: Romm
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 上午9:22
 */

object RoomHelper{
    private  val appDatabase by lazy {
        Room.databaseBuilder(App.instance,AppDatabase::class.java,"database_wanandroid").build()
    }

    private val readHistoryDao by lazy { appDatabase.readHistoryDao() }
    suspend fun queryAllReadHistory()= readHistoryDao.queryAll().map { it.article.apply { tags=it.tags } }.reversed()

    suspend fun addReadHistory(article: Article){
        readHistoryDao.queryArticle(article.id)?.let {
            readHistoryDao.deleteArticle(it)
        }

        readHistoryDao.insertArticle(article.apply { primaryKeyId=0 })
        article.tags.forEach{
            readHistoryDao.insertArticleTag(Tag(
                id=0,articleId = article.id.toLong(),name = it.name,url = it.url
            ))
        }
    }

    suspend fun deleteReadHistory(article: Article)= readHistoryDao.deleteArticle(article)
}