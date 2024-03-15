package com.zhalz.friendzy.ui.register

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.colorRes
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseActivity
import com.zhalz.friendzy.databinding.ActivityRegisterBinding
import com.zhalz.friendzy.ui.login.LoginActivity
import com.zhalz.friendzy.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register) {

    var name = ""
    var phone = ""
    var password = ""
    var confirmPassword = ""

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

    private fun observe() {
        lifecycleScope.launch {
            viewModel.registerResponse.collect {
                it.let {
                    when (it.status) {
                        ApiStatus.LOADING -> loadingDialog.show("Signing Up...")
                        ApiStatus.SUCCESS -> {
                            loadingDialog.dismiss()
                            toHome()
                        }
                        else -> return@collect

                    }
                }
            }
        }
        viewModel.message.observe(this@RegisterActivity) {
            tos(it)
            loadingDialog.dismiss()
        }
    }

    fun validateRegister() {
        if (name.isEmpty()) binding.etName.error = getString(R.string.msg_error)
        if (phone.isEmpty()) binding.etPhone.error = getString(R.string.msg_error)
        if (password.isEmpty()) binding.etPassword.error = getString(R.string.msg_error)
        if (confirmPassword.isEmpty()) binding.etConfirmPassword.error = getString(R.string.msg_error)
        else if (confirmPassword != password) {
            binding.etPassword.error = getString(R.string.msg_error_password)
            binding.etConfirmPassword.error = getString(R.string.msg_error_password)
        }
        else if (name.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            viewModel.register(name, phone, password)
        }
    }

    private fun toHome() {
        openActivity<MainActivity>()
        finish()
    }

    fun toLogin() {
        openActivity<LoginActivity>()
        finish()
    }

    fun back() = finish()

}