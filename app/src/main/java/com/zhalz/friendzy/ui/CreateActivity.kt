package com.zhalz.friendzy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.ActivityCreateBinding

class CreateActivity : AppCompatActivity() {

    private val binding: ActivityCreateBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_create)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
    }

    fun finishActivity() {
        finish()
    }
}