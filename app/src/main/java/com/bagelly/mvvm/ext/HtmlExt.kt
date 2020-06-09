package com.bagelly.mvvm.ext

import androidx.core.text.HtmlCompat

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ext
 * @ClassName: HtmlExt
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/9 上午10:07
 */
fun String?.htmlToSpanned()=
    if (this.isNullOrEmpty())"" else HtmlCompat.fromHtml(this,HtmlCompat.FROM_HTML_MODE_LEGACY)
