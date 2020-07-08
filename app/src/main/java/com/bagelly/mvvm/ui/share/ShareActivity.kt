package com.bagelly.mvvm.ui.share
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ext.hideSoftInput
import com.bagelly.mvvm.ext.showToast
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_settings.ivBack
import kotlinx.android.synthetic.main.activity_share.*
class ShareActivity : BaseVmActivity<ShareViewModel>() {
    override fun viewModelClass() = ShareViewModel::class.java
    override fun layoutRes() = R.layout.activity_share

    override fun initView() {
        ivBack.setOnClickListener { ActivityManager.finish(ShareActivity::class.java) }
        acetlink.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tvSubmit.performClick()
                true
            } else {
                false
            }
        }

        tvSubmit.setOnClickListener {
            val title=acetTitle.text.toString().trim()
            val link=acetlink.text.toString().trim()
            if (title.isEmpty()){
                showToast(R.string.title_toast)
                return@setOnClickListener
            }

            if (link.isEmpty()){
                showToast(R.string.link_toast)
                return@setOnClickListener
            }
            tvSubmit.hideSoftInput()
            mViewModel.shareArticle(title,link)
        }
    }


    override fun initData() {
        mViewModel.getUserInfo()
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            userInfo.observe(this@ShareActivity, Observer {
                val sharePeople=if (it.nickname.isEmpty()) it.username else it.nickname
                 acetSharePeople.setText(sharePeople)
            })
            submitting.observe(this@ShareActivity, Observer {
                if (it) showProgressDialog(R.string.share_article) else hideProgressDialog()
            })

            shareResult.observe(this@ShareActivity, Observer {
                if (it){
                    showToast(R.string.share_article_success)
                }
            })
        }
    }

}