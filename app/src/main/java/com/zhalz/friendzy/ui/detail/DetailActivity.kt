package com.zhalz.friendzy.ui.detail

import android.os.Bundle
import com.crocodic.core.base.activity.NoViewModelActivity
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.ActivityDetailBinding
import com.zhalz.friendzy.ui.modify.ModifyActivity

class DetailActivity : NoViewModelActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    var id = 0
    var name = ""
    var school = ""
    var description = ""
    var photo = ""
    private var liked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        binding.title.isSelected = true

        id = intent.getIntExtra("id", 0)
        name = intent.getStringExtra("name") ?: ""
        school = intent.getStringExtra("school") ?: ""
        description = intent.getStringExtra("description") ?: ""
        photo = intent.getStringExtra("photo") ?: ""
        liked = intent.getBooleanExtra("liked", false)

        toolbarConfig()
    }

    private fun toolbarConfig() {
        if (liked) {
            binding.toolbar.menu
                .findItem(R.id.edit)
                .setIcon(R.drawable.ic_star_filled)
        }
    }

    fun toEdit(){
        openActivity<ModifyActivity> {
            putExtra("id", id)
            putExtra("name", name)
            putExtra("school", school)
            putExtra("description", description)
        }
        finish()
    }

    fun finishActivity() = finish()

}