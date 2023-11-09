package com.zhalz.friendzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.friendzy.adapter.FriendAdapter
import com.zhalz.friendzy.data.database.AppDatabase
import com.zhalz.friendzy.data.database.FriendDao
import com.zhalz.friendzy.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val friendManager: FriendDao by lazy {
        AppDatabase.getInstance(this).friendDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.cardItem.setOnClickListener {
            val toDetail = Intent(this, DetailActivity::class.java)
            startActivity(toDetail)
        }

        lifecycleScope.launch {
            friendManager.getAll().collect{
                binding.friendAdapter = FriendAdapter(it)

            }
        }


    }
}