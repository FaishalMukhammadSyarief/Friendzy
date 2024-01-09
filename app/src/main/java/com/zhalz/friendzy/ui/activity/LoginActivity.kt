package com.zhalz.friendzy.ui.activity

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.openActivity
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
        binding.title = "Welcome \nBack!"

        observe()

    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loginResponse.collect {
                        if (it.status == ApiStatus.LOADING) {
                            loadingDialog.show("Logging in...")
                        } else if (it.status == ApiStatus.SUCCESS) {
                            loadingDialog.dismiss()
                            openActivity<MainActivity>()
                            finish()
                        }
                    }
                }
            }
        }
    }


    fun validateLogin() {
        if (phone.isEmpty()) binding.etPhone.error = getString(R.string.msg_error)
        if (password.isEmpty()) binding.etPassword.error = getString(R.string.msg_error)

        else {
            viewModel.login(phone, password)
            openActivity<MainActivity>()
        }
    }

    fun back() {
        finish()
    }
}