package com.bagelly.mvvm.ui.search.result

import com.bagelly.mvvm.ui.common.CollectRepository

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.search.result
 * @ClassName: SearchResultViewModel
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/19 下午5:20
 */
class SearchResultViewModel {
    companion object{
        const val INITIAL_PAGE = 0
    }

    private val searchResultRepository by lazy { SearchResultRepository() }
    private val collectRepository by lazy { CollectRepository() }
}