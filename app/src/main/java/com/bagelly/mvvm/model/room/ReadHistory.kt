package com.bagelly.mvvm.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.bean.Tag

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.room
 * @ClassName: ReadHistory
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/13 上午11:28
 */
data  class ReadHistory (
    @Embedded
    var article: Article,
    @Relation(parentColumn = "id",entityColumn = "article_id")
    var tags:List<Tag>

)
