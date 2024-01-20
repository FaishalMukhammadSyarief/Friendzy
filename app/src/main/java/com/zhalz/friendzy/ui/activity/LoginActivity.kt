package com.zhalz.friendzy.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.colorRes
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseActivity
import com.zhalz.friendzy.databinding.ActivityLoginBinding
import com.zhalz.friendzy.ui.viewmodel.LoginViewModel
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

    private fun observe() {
        lifecycleScope.launch {

            viewModel.loginResponse.collect {
                if (it.status == ApiStatus.LOADING) {
                    loadingDialog.show("Logging in...")
                } else if (it.status == ApiStatus.SUCCESS) {
                    loadingDialog.dismiss()
                }

            }
        }
    }

    private fun initUI() {

        binding.title = "Welcome \nBack!"

        window.apply {
            statusBarColor = colorRes(R.color.green)
        }

    }

    fun validateLogin() {

        if (phone.isEmpty()) binding.etPhone.error = getString(R.string.msg_error)
        if (password.isEmpty()) binding.etPassword.error = getString(R.string.msg_error)

        if (phone.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(phone, password)

            lifecycleScope.launch {
                viewModel.loginResponse.collect {
                    it.let {
                        if (it.status == ApiStatus.SUCCESS) {
                            toHome()
                        }
                    }
                }
            }
        }

    }

    private fun toHome() {
        val toHome = Intent(this, MainActivity::class.java)
        toHome.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(toHome)
    }

    fun back() {
        finish()
    }
}