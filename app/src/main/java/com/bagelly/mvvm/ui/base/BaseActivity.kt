package com.bagelly.mvvm.ui.base

import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import com.bagelly.mvvm.common.ProgressDialogFragment

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.base
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 上午11:15
 */

abstract class BaseActivity :AppCompatActivity(){
    private  lateinit var progressDiglogFragment: ProgressDialogFragment
    /**
     * 加载状态有4种
     * 1.整页面数据加载，加载动画在中间
     * 2.下拉刷新
     * 3.分页加载
     * 4.数据提交服务器加载对话框
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
    }

    open fun layoutRes(): Int =0

    fun showProgressDialog(@StringRes message:Int){
        if (!this::progressDiglogFragment.isInitialized){
            progressDiglogFragment=ProgressDialogFragment.newInstance()
        }
        progressDiglogFragment.show(supportFragmentManager,message,false)
    }

    fun showProgressDialog(){
        if (this::progressDiglogFragment.isInitialized&&progressDiglogFragment.isVisible){
            progressDiglogFragment.dismiss()
        }
    }
}