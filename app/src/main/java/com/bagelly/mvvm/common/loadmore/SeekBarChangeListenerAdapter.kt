package com.bagelly.mvvm.common.loadmore

import android.widget.SeekBar

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.common.loadmore
 * @ClassName: SeekBarChangeListenerAdapter
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/7/7 下午4:27
 */
class SeekBarChangeListenerAdapter(
    private val onProgressChanged:((seekBar:SeekBar?,progress:Int,fromUser:Boolean)->Unit)?=null,
    private val onStartTrackingTouch:((seekBar:SeekBar?)->Unit)?=null,
    private val onStopTrackingTouch:((seekBar:SeekBar?)->Unit)?=null
):SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onProgressChanged?.invoke(seekBar,progress,fromUser)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        onStartTrackingTouch?.invoke(seekBar)
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        onStopTrackingTouch?.invoke(seekBar)
    }
}