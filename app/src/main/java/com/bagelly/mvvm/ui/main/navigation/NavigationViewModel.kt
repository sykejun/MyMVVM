package com.bagelly.mvvm.ui.main.navigation
import androidx.lifecycle.MutableLiveData
import com.bagelly.mvvm.model.bean.Navigation
import com.bagelly.mvvm.ui.base.BaseViewModel
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.navigation
 * @ClassName: NavigationViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 上午9:22
 */
class NavigationViewModel :BaseViewModel() {
    private val navigationRepository by lazy { NavigationRepository() }
    val navigatons=MutableLiveData<List<Navigation>>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    fun getNavigations(){
        refreshStatus.value = true
        reloadStatus.value=false

        launch(
            block = {
                navigatons.value=navigationRepository.getNavigations()
                refreshStatus.value=false
            },
            error = {
                refreshStatus.value=false
                reloadStatus.value=navigatons.value.isNullOrEmpty()
            }
        )
    }
}