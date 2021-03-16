package com.bagelly.mvvm.model.store

import com.tencent.mmkv.MMKV

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.model.store
 * @ClassName: SettingStore
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 上午10:32
 */

object SettingsStore{
//    val mmkv = MMKV.defaultMMKV()//使用MMKV来存储数据
//    private  const val DEFAULT_WEB_TEXT_ZOOM=100
//    private const val KEY_WEB_TEXT_ZOOM="KEY_WEB_TEXT_ZOOM"
//    private const val KEY_NIGHT_MODE="KEY_NIGHT_MODE"
//
//    fun setWebTextZoom(textZoom:Int)= mmkv.encode(KEY_WEB_TEXT_ZOOM,textZoom)
//
//    fun getWebTextZoom():Int=mmkv.decodeInt(KEY_WEB_TEXT_ZOOM, DEFAULT_WEB_TEXT_ZOOM)
//
//    fun setNightMode(nightMode: Boolean)= mmkv.encode(KEY_NIGHT_MODE,nightMode)
//
//    fun getNightMode():Boolean= mmkv.decodeBool(KEY_NIGHT_MODE,false)

    private val mmkv by lazy { MMKV.defaultMMKV() }
    private const val DEFAULT_WEB_TEXT_ZOOM=100
    private const val KEY_WEB_TEXT_ZOOM="KEY_WEB_TEXT_ZOOM"
    private const val KEY_NIGHT_MODE="KEY_NIGHT_MODE"

    fun setWebTextZoom(textZoom:Int)= mmkv.encode(KEY_NIGHT_MODE,textZoom)
    fun getWebTextZoom()= mmkv.decodeInt(KEY_NIGHT_MODE, DEFAULT_WEB_TEXT_ZOOM)

    fun setNightMode(nightMode:Boolean)= mmkv.encode(KEY_NIGHT_MODE,nightMode)
    fun getNightMode()= mmkv.decodeBool(KEY_NIGHT_MODE,false)

}