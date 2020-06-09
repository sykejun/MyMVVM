package com.bagelly.mvvm.common.loadmore

import com.bagelly.mvvm.R
import com.chad.library.adapter.base.loadmore.LoadMoreView

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.common.loadmore
 * @ClassName: CommonLoadMoreView
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/9 上午10:23
 */
class CommonLoadMoreView :LoadMoreView() {
    override fun getLayoutId(): Int = R.layout.view_load_more_common

    override fun getLoadingViewId(): Int =R.id.load_more_loading_view

    override fun getLoadEndViewId(): Int=R.id.load_more_load_end_view

    override fun getLoadFailViewId(): Int=R.id.load_more_load_fail_view

}