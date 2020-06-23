package com.bagelly.mvvm.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagelly.mvvm.App
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ext.showToast
import com.bagelly.mvvm.model.api.ApiException
import com.bagelly.mvvm.ui.common.UserRepository
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.google.gson.JsonParseException
import kotlinx.coroutines.*
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketException

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.base
 * @ClassName: BaseViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/5 下午2:05
 */

typealias  Block<T> = suspend () -> T
typealias  Error = suspend (e: Exception) -> Unit
typealias Cancel =suspend (e: Exception) -> Unit

open class BaseViewModel : ViewModel() {
    protected  val userRepository by lazy { UserRepository() }
       val loginStateInvlid:MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @return Job
     */

    protected  fun launch(block: Block<Unit>,error: Error?=null,cancel: Cancel?=null): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            }catch (e:Exception){
                when(e){
                    is CancellationException ->{
                        cancel?.invoke(e)
                    }
                    else ->{
                        onError(e)
                        error?.invoke(e)
                    }
                }
            }
        }
    }
    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke() }
    }


    /**
     * 取消协程
     * @param job 协程job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * 统一处理错误的异常
     */
    private fun onError(e: Exception) {
      when(e){
          is ApiException ->{
              when(e.code){
                  -1001->{
                      //登录失效
                      userRepository.clearLoginState()
                      Bus.post(USER_LOGIN_STATE_CHANGED,false)
                      loginStateInvlid.value=false//登录失效了
                  }

                  -1 ->{
                      App.instance.showToast(e.message)
                  }

                  else ->{
                      App.instance.showToast(e.message)
                  }
              }
          }

          is ConnectException ->{
              //链接异常
              App.instance.showToast(App.instance.getString(R.string.network_connection_failed))
          }

          is SocketException ->{
              // 请求超时
              App.instance.showToast(App.instance.getString(R.string.network_request_timeout))
          }

          is JsonParseException ->{
              // 数据解析错误
              App.instance.showToast(App.instance.getString(R.string.api_data_parse_error))
          }

          else ->{
              e.message?.let { App.instance.showToast(it) }
          }
      }

    }


    fun loginStatus()=userRepository.isLogin()
}