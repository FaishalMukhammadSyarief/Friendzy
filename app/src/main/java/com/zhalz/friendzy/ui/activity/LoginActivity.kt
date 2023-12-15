package com.zhalz.friendzy.ui.activity

import android.os.Bundle
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.ActivityLoginBinding

class LoginActivity : NoViewModelActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        binding.title = "Welcome \nBack!"

    }

    fun toHome() {
        openActivity<MainActivity>()
    }

    fun back() {
        finish()
    }
}