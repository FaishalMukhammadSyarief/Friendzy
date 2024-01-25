package com.zhalz.friendzy.ui.activity

import android.os.Bundle
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.colorRes
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.ActivityRegisterBinding

class RegisterActivity : NoViewModelActivity<ActivityRegisterBinding>(R.layout.activity_register) {

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

    fun toHome() {
        openActivity<MainActivity>()
        finish()
    }

    fun toLogin() {
        openActivity<LoginActivity>()
        finish()
    }

    fun back() = finish()

}