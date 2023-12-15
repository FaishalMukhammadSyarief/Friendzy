package com.zhalz.friendzy.ui.activity

import android.os.Bundle
import androidx.core.view.WindowCompat
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.ActivityWelcomeBinding

class WelcomeActivity : NoViewModelActivity<ActivityWelcomeBinding>(R.layout.activity_welcome) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun toLogin() {
        openActivity<LoginActivity>()
    }
}