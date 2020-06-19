package com.bagelly.mvvm.ui.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bagelly.mvvm.ui.login.LoginActivity
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManger.start

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.base
 * @ClassName: BaseVmFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午4:47
 */
abstract  class BaseVmFragment <VM:BaseViewModel>:BaseFragment() {

    protected  lateinit var mViewModel: VM
    private  var lazyLoaded=false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        observer()
        // 因为Activity恢复后savedInstanceState不为null，
        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
        // 不需要重新初始化数据。
        if (savedInstanceState==null){
            initData()
        }
    }

      open fun initData() {
        // Override if need
    }

    open fun observer() {
        mViewModel.loginStateInvlid.observe(viewLifecycleOwner, Observer {
            if (it){
                Bus.post(USER_LOGIN_STATE_CHANGED,false)
                // TODO: 2020/6/5  loglin
//                ActivityManger.start()
            }
        })

    }
    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }

    open  fun lazyLoadData() {

    }

    private fun initViewModel() {
        mViewModel=ViewModelProvider(this).get(viewModelClass())
    }
    abstract fun viewModelClass(): Class<VM>
    open fun initView() {
        //override if need
    }


    /**
     * 是否登录，如果登录了就执行then，没有登录就直接跳转登录界面
     * @return true-已登录，false-未登录
     */
    fun checkLogin(then: (() -> Unit)? = null): Boolean {
        return if (mViewModel.loginStatus()) {
            then?.invoke()
            true
        } else {
          start(LoginActivity::class.java)
            false
        }
    }
}