package com.bagelly.mvvm.ext

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ext
 * @ClassName: FragmentExt
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午1:46
 */

fun Fragment.openInExplorer(link:String?){
    startActivity(Intent().apply{
        action=Intent.ACTION_VIEW
        data= Uri.parse(link)
    })
}