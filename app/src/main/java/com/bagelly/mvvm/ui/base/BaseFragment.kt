package com.bagelly.mvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.base
 * @ClassName: BaseFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午2:02
 */

open class BaseFragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes(),container,false)
    }

    private fun layoutRes()=0
}