package com.zhalz.friendzy.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.colorRes
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseActivity
import com.zhalz.friendzy.databinding.ActivityRegisterBinding
import com.zhalz.friendzy.ui.viewmodel.RegisterViewModel
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

    }

    private fun initUI() {

        window.apply {
            statusBarColor = colorRes(R.color.green)
        }

    }

    private fun register() {
        viewModel.register(name, phone, password)

        lifecycleScope.launch {
            viewModel.registerResponse.collect {
                it.let {
                    if (it.status == ApiStatus.LOADING) loadingDialog.show()
                    else if (it.status == ApiStatus.SUCCESS) {
                        loadingDialog.dismiss()
                        toHome()
                    }
                }
            }
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
        else {
            register()
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