package com.bagelly.mvvm.ui.main.system.pager

import com.bagelly.mvvm.model.bean.Category
import com.bagelly.mvvm.ui.base.BaseFragment

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.system.pager
 * @ClassName: SystemPagerFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/22 下午2:26
 */
class SystemPagerFragment :BaseFragment(){
    var checkedPosition = 0
    companion object{
        fun  newInstance(categoryList: ArrayList<Category>)=SystemPagerFragment()
    }

     fun scrollToTop() {

    }
}