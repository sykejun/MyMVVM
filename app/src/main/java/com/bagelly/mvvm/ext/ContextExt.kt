package com.bagelly.mvvm.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ext
 * @ClassName: ContextExt
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午4:10
 */

/**
 * 实现将特定文本复制到剪贴板的功能。
 * @param[label] User-visible label for the clip data.
 * @param[text] The actual text in the clip.
 */
fun Context.copyTextIntoClipboard(text:CharSequence?,label:String?=""){
    if (text.isNullOrEmpty()) return
    val cbs=applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?:return
    cbs.setPrimaryClip(ClipData.newPlainText(label,text))
}

fun Context.showToast(message:CharSequence){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes message:Int){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}