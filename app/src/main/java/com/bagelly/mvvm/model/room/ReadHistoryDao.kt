package com.bagelly.mvvm.model.room

import androidx.room.*
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.bean.Tag

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.room
 * @ClassName: ReadHistoryDao
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/12 下午5:13
 */
@Dao
interface ReadHistoryDao {
    @Transaction
    @Insert(entity = Article::class)
    suspend fun insertArticle(article: Article):Long

    @Transaction
    @Insert(entity = Tag::class)
    suspend fun insertArticleTag(tag: Tag):Long

    @Transaction
    @Query("SELECT * FROM article")
    suspend fun queryAll():List<ReadHistory>

    @Transaction
    @Query("SELECT * FROM article WHERE id = (:id)")
    suspend fun queryArticle(id:Int):Article?

    @Transaction
    @Delete(entity = Article::class)
    suspend fun deleteArticle(article: Article)

    @Transaction
    @Update(entity = Article::class)
    suspend fun updateArticle(article: Article)

}