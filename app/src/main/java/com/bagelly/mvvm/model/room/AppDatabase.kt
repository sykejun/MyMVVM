package com.bagelly.mvvm.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.bean.Tag

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.room
 * @ClassName: AppDatabase
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/12 下午5:10
 */
@Database(entities = [Article::class, Tag::class], version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun readHistoryDao():ReadHistoryDao
}