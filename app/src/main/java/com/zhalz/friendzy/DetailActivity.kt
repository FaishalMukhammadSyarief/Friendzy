package com.zhalz.friendzy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zhalz.friendzy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding.title.isSelected = true

        binding.toolbar.setNavigationOnClickListener {
            back()
        }
    }

    private fun back() {
        onBackPressedDispatcher.onBackPressed()
    }
}