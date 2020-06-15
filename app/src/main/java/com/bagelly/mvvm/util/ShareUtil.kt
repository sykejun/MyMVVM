package com.bagelly.mvvm.util

import android.app.Activity
import androidx.core.app.ShareCompat
import com.bagelly.mvvm.R

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.util
 * @ClassName: ShareUtil
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午1:41
 */

fun share(activity:Activity, title:String=activity.getString(R.string.app_name),content:String?){
    ShareCompat.IntentBuilder.from(activity)
        .setType("text/plain")
        .setSubject(title)
        .setText(content)
        .setChooserTitle(title)
        .startChooser()
}