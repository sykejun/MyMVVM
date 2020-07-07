package com.bagelly.mvvm.ui.register

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.bagelly.mvvm.R
import com.bagelly.mvvm.ui.base.BaseVmActivity
import com.bagelly.mvvm.ui.login.LoginActivity
import com.bagelly.mvvm.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_detail.ivBack
import kotlinx.android.synthetic.main.activity_register.*

/**
 *
 * @ProjectName: MyMVVM
 * @Package: com.bagelly.mvvm.ui.register
 * @ClassName: RegisterActivity
 * @Description: java类作用描述
 * @Author: bagelly
 * QQ:774169396
 * @CreateDate: 2020/6/15 下午5:20
 */
class RegisterActivity :BaseVmActivity<RegisterViewModel>(){
    override fun viewModelClass()=RegisterViewModel::class.java
    override fun layoutRes()= R.layout.activity_register

    override fun initView() {
        ivBack.setOnClickListener { finish() }
        tietConfirmPssword.setOnEditorActionListener { v, actionId, event ->
            if (actionId==EditorInfo.IME_ACTION_GO){
                btnRegister.performClick()
                true
            }else{
                false
            }
        }

        btnRegister.setOnClickListener {
            tilAccount.error=""
            tilPassword.error=""
            tilConfirmPssword.error=""
            val account=tietAccount.text.toString()
            val password=tietPassword.text.toString()
            val confirmPassword=tietConfirmPssword.text.toString()
            when {
                account.isEmpty() -> tilAccount.error = getString(R.string.account_can_not_be_empty)
                account.length < 3 -> tilAccount.error =
                    getString(R.string.account_length_over_three)
                password.isEmpty() -> tilPassword.error =
                    getString(R.string.password_can_not_be_empty)
                password.length < 6 -> tilPassword.error =
                    getString(R.string.password_length_over_six)
                confirmPassword.isEmpty() -> tilConfirmPssword.error =
                    getString(R.string.confirm_password_can_not_be_empty)
                password != confirmPassword -> tilConfirmPssword.error =
                    getString(R.string.two_password_are_inconsistent)
                else -> mViewModel.register(account, password, confirmPassword)
            }

        }
    }

    override fun observer() {
        super.observer()
        mViewModel.run {
            submitting.observe(this@RegisterActivity, Observer {
                if (it) showProgressDialog(R.string.registerring) else hideProgressDialog()
            })
            registerResult.observe(this@RegisterActivity, Observer {
                if (it) ActivityManager.finish(LoginActivity::class.java, RegisterActivity::class.java)
            })

        }
    }
}