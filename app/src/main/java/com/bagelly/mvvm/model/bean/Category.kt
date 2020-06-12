package com.bagelly.mvvm.model.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.bean
 * @ClassName: Category
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/11 上午9:43
 */
@Parcelize
data class Category(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: ArrayList<Category>
) : Parcelable

