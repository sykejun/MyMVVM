package com.bagelly.mvvm.ui.login

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.ui.register.RegisterActivity
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_login.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.login
 * @ClassName: LoginActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午4:25
 */
class LoginActivity :BaseVmActivity<LoginViewModel>() {
    override fun viewModelClass()=LoginViewModel::class.java
    override fun layoutRes()= R.layout.activity_login

    override fun initView() {
        ivClose.setOnClickListener { finish() }
        tvGoRegister.setOnClickListener {
            ActivityManager.start(RegisterActivity::class.java)
        }

        tietPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO){
                btnLogin.performClick()
                true
        }else{
                false
            }
        }

        btnLogin.setOnClickListener {
            tilAccount.error=""
            tilPassword.error=""
            val account=tietAccount.text.toString()
            val password=tietPassword.text.toString()
            when{
                account.isEmpty() ->tilAccount.error=getString(R.string.account_can_not_be_empty)
                password.isEmpty() ->tilPassword.error=getString(R.string.password_can_not_be_empty)
                else ->mViewModel.login(account,password)
            }
        }
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            submitting.observe(this@LoginActivity, Observer {
                if (it) showProgressDialog(R.string.logging_in) else hideProgressDialog()
            })


            loginResult.observe(this@LoginActivity, Observer {
                if (it){
                    ActivityManager.finish(LoginActivity::class.java)
                }
            })
        }
    }
}