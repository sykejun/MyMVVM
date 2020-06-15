package com.bagelly.mvvm.ui.detail

import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.ui.base.BaseViewModel
import com.bagelly.mvvm.ui.common.CollectRepository

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.detail
 * @ClassName: DetailViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 上午9:39
 */

class DetailViewModel : BaseViewModel(){
    private val detailRepository by lazy { DetailRepository() }
    private val collectRepository by lazy { CollectRepository() }
    val collect= MutableLiveData<Boolean>()

    fun collect(id:Int){
        launch(
            block = {
                collectRepository.collect(id)
                //收藏成功，更新userinfo
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply { if (!collectIds.contains(id)) collectIds.add(id) })
                collect.value=true
            },
            error = {
                collect.value=false
            }
        )
    }


    fun uncollect(id: Int){
        launch(
            block = {
                collectRepository.uncollect(id)
                //取消收藏跟新个人信息UserInfo
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                collect.value=false
        },
            error ={
                collect.value=true
            } )
    }


    fun updateCollectState(id: Int){
        collect.value=if (userRepository.isLogin()){
            userRepository.getUserInfo()!!.collectIds.contains(id)
        }else{
            false
        }
    }


    fun saveReadHistory(article: Article){
        launch(block = {
            detailRepository.saveReadHistory(article)
        })
    }
}