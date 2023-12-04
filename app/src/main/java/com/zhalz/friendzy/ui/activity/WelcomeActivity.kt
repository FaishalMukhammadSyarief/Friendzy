package com.zhalz.friendzy.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private val binding: ActivityWelcomeBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_welcome) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        binding.activity = this

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun toHome() {
        val toHome = Intent(this, MainActivity::class.java)
        startActivity(toHome)
        finish()
    }
}