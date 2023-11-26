package com.zhalz.friendzy.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_detail) }

    var name = ""
    var birth = ""
    var description = ""
    var photo = ""
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        binding.title.isSelected = true

        name = intent.getStringExtra("name") ?: ""
        birth = intent.getStringExtra("birth") ?: ""
        description = intent.getStringExtra("description") ?: ""
        photo = intent.getStringExtra("photo") ?: ""
        id = intent.getIntExtra("id", 0)
    }

    fun toEdit(){
        val toEdit = Intent(this, ModifyActivity::class.java).apply {
            putExtra("name", name)
            putExtra("birth", birth)
            putExtra("description", description)
            putExtra("photo", photo)
            putExtra("id", id)
        }
        startActivity(toEdit)
        finish()
    }

    fun finishActivity() {
        finish()
    }

}