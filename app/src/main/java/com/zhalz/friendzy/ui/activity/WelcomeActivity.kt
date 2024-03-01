package com.zhalz.friendzy.ui.activity

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseActivity
import com.zhalz.friendzy.databinding.ActivityWelcomeBinding
import com.zhalz.friendzy.ui.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding, WelcomeViewModel>(R.layout.activity_welcome) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.activity = this

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun setDestination() {
        lifecycleScope.launch(Dispatchers.IO) {
            if (viewModel.checkLogin()) toHome()
            else toLogin()
        }

    }

    private fun toHome() {
        openActivity<MainActivity>()
        finish()
    }

    private fun toLogin() {
        openActivity<LoginActivity>()
        finish()
    }

    fun toRegister() {
        openActivity<RegisterActivity>()
        finish()
    }

}