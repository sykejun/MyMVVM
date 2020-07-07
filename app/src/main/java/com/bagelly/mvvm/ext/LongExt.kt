package com.bagelly.mvvm.ext

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ext
 * @ClassName: LongExt
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午4:05
 */
fun Long.toDateTime(pattern:String)=SimpleDateFormat(pattern, Locale.getDefault()).format(this)