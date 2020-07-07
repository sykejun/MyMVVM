package com.bagelly.mvvm.ui.settings
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.BuildConfig
import com.bagelly.mvvm.R
import com.bagelly.mvvm.common.loadmore.SeekBarChangeListenerAdapter
import com.bagelly.mvvm.ext.setNavigationBarColor
import com.bagelly.mvvm.ext.showToast
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.model.store.SettingsStore
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.detail.DetailActivity.Companion.PARAM_ARTICLE
import com.bagelly.mvvm.ui.login.LoginActivity
import com.bagelly.mvvm.util.core.ActivityManager
import com.bagelly.mvvm.util.core.clearCache
import com.bagelly.mvvm.util.core.getCacheSize
import com.bagelly.mvvm.util.isNightMode
import com.bagelly.mvvm.util.setNightMode
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.view_change_text_zoom.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.settings
 * @ClassName: SettingActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/29 下午3:05
 */
class SettingActivity :BaseVmActivity<SettingViewModel>(){
    override fun layoutRes()= R.layout.activity_settings
    override fun viewModelClass()=SettingViewModel::class.java

    override fun initView() {
        setNavigationBarColor(R.color.bgColorPrimary)
        scDayNight.isChecked= isNightMode(this)
        tvFontSize.text="${SettingsStore.getWebTextZoom()}"
        tvClearCache.text=getCacheSize(this)
        tvAboutUs.text=getString(R.string.current_version, BuildConfig.VERSION_NAME)
        ivBack.setOnClickListener { ActivityManager.finish(SettingActivity::class.java) }
        scDayNight.setOnCheckedChangeListener { _, checked ->
            setNightMode(checked)
            SettingsStore.setNightMode(checked)
        }

        llFontSize.setOnClickListener {
            setFontSize()
        }

        llFontSize.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.clear_cache)
                .setNegativeButton(R.string.cancel){_, _ ->}
                .setPositiveButton(R.string.confirm){dialog, which ->
                    clearCache(this)
                    tvClearCache.text= getCacheSize(this)
                }
                .show()

        }

        llCheckVersion.setOnClickListener { showToast(R.string.stay_tuned) }

        llAboutUs.setOnClickListener {
            ActivityManager.start(
                DetailActivity::class.java,
                mapOf(
                    PARAM_ARTICLE to Article(
                        title = getString(R.string.abount_us),
                        link = "https://github.com/xiaoyanger0825/wanandroid"
                    )
                )
            )
        }

        tvLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(R.string.confirm){ _, _ ->
                    mViewModel.logout()
                    ActivityManager.start(LoginActivity::class.java)
                }
                .setNegativeButton(R.string.cancel){_, _ -> }
                .show()
        }
    }
    private fun setFontSize() {
        val textZoom = SettingsStore.getWebTextZoom()
        var tempTextZoom = textZoom
        AlertDialog.Builder(this)
            .setTitle(R.string.font_size)
            .setView(LayoutInflater.from(this).inflate(R.layout.view_change_text_zoom, null).apply {
                seekBar.progress = textZoom - 50
                seekBar.setOnSeekBarChangeListener(SeekBarChangeListenerAdapter(
                    onProgressChanged = { _, progress, _ ->
                        tempTextZoom = 50 + progress
                        tvFontSize.text = "$tempTextZoom%"
                    }
                ))
            })
            .setNegativeButton(R.string.cancel) { _, _ ->
                tvFontSize.text = "$textZoom%"
            }
            .setPositiveButton(R.string.confirm) { _, _ ->
                SettingsStore.setWebTextZoom(tempTextZoom)
            }
            .show()

    }

    override fun initData() {
        mViewModel.getLoginStatus()
    }

    override fun observer() {
        super.observer()
        mViewModel.isLogin.observe(this, Observer {
            tvLogout.isVisible=it
        })
    }
}