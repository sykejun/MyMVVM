package com.bagelly.mvvm.model.store

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import javax.net.ssl.HostnameVerifier

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.store
 * @ClassName: SearchHistoryStore
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/17 下午4:59
 */

object SearchHistoryStore {
    private const val MK_SEARCH_HISTORY="MK_SEARCH_HISTORY"
    val mmkv = MMKV.defaultMMKV()//使用MMKV来存储数据
    private val mGson by lazy { Gson()}

    fun saveSearchHistory(words:String){
        val history=getSearchHistory()
        if (history.contains(words)){
            history.remove(words)
        }
        history.add(0,words)
        val listStr= mGson.toJson(history)
        mmkv.encode(MK_SEARCH_HISTORY,listStr)
    }

    private fun getSearchHistory(): MutableList<String> {
        val listStr= mmkv.decodeString(MK_SEARCH_HISTORY,"")
        return if (listStr.isEmpty()){
            mutableListOf()
        }else{
            mGson.fromJson(
                listStr,
                object : TypeToken<MutableList<String>>() {}.type
            )
        }
    }

    fun deleteSearchHistory(words: String){
        val history = getSearchHistory()
        history.remove(words)
        val listStr= mGson.toJson(history)
        mmkv.encode(MK_SEARCH_HISTORY,listStr)
    }
}