package com.zhalz.friendzy.ui.detail

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseActivity
import com.zhalz.friendzy.databinding.ActivityDetailBinding
import com.zhalz.friendzy.ui.modify.ModifyActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {

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

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.edit -> {
                    likeFriend()
                    if (liked) {
                        binding.toolbar.menu
                            .findItem(R.id.edit)
                            .setIcon(R.drawable.ic_star_empty)
                    }
                    else if (!liked) {
                        binding.toolbar.menu
                            .findItem(R.id.edit)
                            .setIcon(R.drawable.ic_star_filled)
                    }
                }
            }
            true
        }
    }

    private fun likeFriend() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel.getUserID().let {
            viewModel.likeFriend(it, id)
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