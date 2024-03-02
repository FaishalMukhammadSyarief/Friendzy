package com.zhalz.friendzy.ui.detail

import android.os.Bundle
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.ActivityDetailBinding
import com.zhalz.friendzy.ui.modify.ModifyActivity

class DetailActivity : NoViewModelActivity<ActivityDetailBinding>(R.layout.activity_detail) {

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
        openActivity<ModifyActivity> {
            putExtra("name", name)
            putExtra("birth", birth)
            putExtra("description", description)
            putExtra("photo", photo)
            putExtra("id", id)
        }
        finish()
    }

    fun finishActivity() {
        finish()
    }

}