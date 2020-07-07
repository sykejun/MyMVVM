package com.bagelly.mvvm.ui.history

import android.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.main.home.ArticleAdapter
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_COLLECT_UPDATED
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_history.*
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.history
 * @ClassName: HistoryActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午3:00
 */
class HistoryActivity : BaseVmActivity<HistoryViewModel>() {
    private lateinit var mAdapter: ArticleAdapter
    override fun layoutRes() = R.layout.activity_history
    override fun viewModelClass() = HistoryViewModel::class.java
    override fun initView() {
        mAdapter = ArticleAdapter().apply {
            bindToRecyclerView(recyclerView)
            setOnItemClickListener { _, _, position ->
                val article = data[position]
                ActivityManager.start(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_ARTICLE to article)
                )
            }

            setOnItemChildClickListener { _, view, position ->
                val article = data[position]
                if (view.id == R.id.iv_collect && checkLogin()) {
                    view.isSelected = !view.isSelected
                    if (article.collect) {
                        mViewModel.uncollect(article.id)
                    } else {
                        mViewModel.collect(article.id)
                    }

                }
            }
            setOnItemLongClickListener { adapter, view, position ->
                AlertDialog.Builder(this@HistoryActivity)
                    .setMessage(R.string.confirm_delete_history)
                    .setNegativeButton(R.string.cancel) { dialog, which -> }
                    .setPositiveButton(R.string.confirm) { dialog, which ->
                        mViewModel.deleteHistory(data[position])
                        mAdapter.remove(position)
                        this@HistoryActivity.emptyView.isVisible = data.isEmpty()
                    }.show()
                true
            }
        }

        ivBack.setOnClickListener { ActivityManager.finish(HistoryActivity::class.java) }
    }

    override fun initData() {
        mViewModel.getData()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            articleList.observe(this@HistoryActivity, Observer {
                mAdapter.setNewData(it)
            })

            emptyStatus.observe(this@HistoryActivity, Observer {
                emptyView.isVisible = it
            })

        }

        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED, this) {
            mViewModel.updateListCollectState()
        }

        Bus.observe<Pair<Int,Boolean>>(USER_COLLECT_UPDATED,this){
            mViewModel.updateItemCollectState(it)
        }
    }
}
