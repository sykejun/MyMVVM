package com.bagelly.mvvm.ui.main.mine
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.model.bean.Article
import com.bagelly.mvvm.ui.base.BaseVmFragment
import com.bagelly.mvvm.ui.collection.CollectionActivity
import com.bagelly.mvvm.ui.detail.DetailActivity
import com.bagelly.mvvm.ui.detail.DetailActivity.Companion.PARAM_ARTICLE
import com.bagelly.mvvm.ui.history.HistoryActivity
import com.bagelly.mvvm.ui.opensource.OpenSourceActivity
import com.bagelly.mvvm.ui.points.mine.MinePointsActivity
import com.bagelly.mvvm.ui.points.rank.PointsRankActivity
import com.bagelly.mvvm.ui.settings.SettingActivity
import com.bagelly.mvvm.ui.shared.SharedActivity
import com.bagelly.mvvm.util.bus.Bus
import com.bagelly.mvvm.util.bus.USER_LOGIN_STATE_CHANGED
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.fragment_mine.*
/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.main.home
 * @ClassName: HomeFragment
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/8 上午11:12
 */

class MineFragment:BaseVmFragment<MineViewModel>(){

    companion object{
        fun newInstance()=MineFragment()
    }

    override fun viewModelClass()=MineViewModel::class.java

    override fun layoutRes()= R.layout.fragment_mine

    override fun initView() {
        clHeader.setOnClickListener {
            checkLogin{
                // TODO: 2020/6/29 上传图像没做
                  }
        }

        llMyPoints.setOnClickListener {
            checkLogin {
                ActivityManager.start(MinePointsActivity::class.java)
            }
        }

        llPointsRank.setOnClickListener {  ActivityManager.start(PointsRankActivity::class.java) }

        llMyShare.setOnClickListener { checkLogin{ ActivityManager.start(SharedActivity::class.java)} }

        llMyCollect.setOnClickListener { checkLogin { ActivityManager.start(CollectionActivity::class.java)} }
        llHistory.setOnClickListener {ActivityManager.start(HistoryActivity::class.java)  }

        llAboutAuthor.setOnClickListener {
            ActivityManager.start(
                DetailActivity::class.java,
                mapOf(
                    PARAM_ARTICLE to Article(
                        title = getString(R.string.my_about_author),
                        link = "https://github.com/xiaoyanger0825"
                    )
                )
            )
        }

        llOpenSource.setOnClickListener {ActivityManager.start(OpenSourceActivity::class.java)  }

        llSetting.setOnClickListener {ActivityManager.start(SettingActivity::class.java)   }
    }


    override fun observer() {
        super.observer()
        mViewModel.run {
            isLogin.observe(viewLifecycleOwner, Observer {
                tvLoginRegister.isGone=it
                tvNickName.isVisible=it
                tvId.isVisible=it
            })

            userInfo.observe(viewLifecycleOwner, Observer {
                it?.let {
                    tvNickName.text=it.nickname
                    tvId.text="ID: ${it.id}"
                }
            })
        }


        Bus.observe<Boolean>(USER_LOGIN_STATE_CHANGED,viewLifecycleOwner){
            mViewModel.getUserInfo()
        }
    }

    override fun initData() {
        mViewModel.getUserInfo()
    }

}