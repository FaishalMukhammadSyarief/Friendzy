package com.zhalz.friendzy.ui.login

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.colorRes
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseActivity
import com.zhalz.friendzy.databinding.ActivityLoginBinding
import com.zhalz.friendzy.ui.register.RegisterActivity
import com.zhalz.friendzy.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    var phone = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        initUI()
        observe()

    }

    private fun initUI() {
        window.apply {
            statusBarColor = colorRes(R.color.green)
        }
    }

    private fun observe() = lifecycleScope.launch {
        viewModel.loginResponse.collect {
            it.let {
                if (it.status == ApiStatus.LOADING) {
                    loadingDialog.show("Logging in...")
                } else if (it.status == ApiStatus.SUCCESS) {
                    loadingDialog.dismiss()
                    toHome()
                }
            }
        }
    }

    fun validateLogin() {
        if (phone.isEmpty()) binding.etPhone.error = getString(R.string.msg_error)
        if (password.isEmpty()) binding.etPassword.error = getString(R.string.msg_error)
        if (phone.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(phone, password)
        }
    }

    private fun toHome() {
        openActivity<MainActivity>()
        finish()
    }

    fun toRegister() {
        openActivity<RegisterActivity>()
        finish()
    }

    fun back() = finish()
}