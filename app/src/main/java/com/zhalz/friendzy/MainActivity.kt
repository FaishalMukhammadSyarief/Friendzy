package com.zhalz.friendzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zhalz.friendzy.adapter.FriendAdapter
import com.zhalz.friendzy.data.Friend
import com.zhalz.friendzy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.cardItem.setOnClickListener {
            val toDetail = Intent(this, DetailActivity::class.java)
            startActivity(toDetail)
        }

        val friendList = listOf(
            Friend("Fabe", "26 November 2006", "Devout Muslim"),
            Friend("Fabe", "26 November 2006", "Devout Muslim"),
            Friend("Fabe", "26 November 2006", "Devout Muslim"),
            Friend("Fabe", "26 November 2006", "Devout Muslim"),
            Friend("Fabe", "26 November 2006", "Devout Muslim"),
            Friend("Fabe", "26 November 2006", "Devout Muslim"),
            Friend("Fabe", "26 November 2006", "Devout Muslim")
        )

        val friendAdapter = FriendAdapter(friendList)
        binding.friendAdapter = friendAdapter

    }
}