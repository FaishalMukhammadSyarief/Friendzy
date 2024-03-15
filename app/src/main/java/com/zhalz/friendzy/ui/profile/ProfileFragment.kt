package com.zhalz.friendzy.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseFragment
import com.zhalz.friendzy.databinding.FragmentProfileBinding
import com.zhalz.friendzy.ui.modify.ModifyActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

    private var id = 0
    var name = ""
    var school = ""
    var desc = ""
    var photo = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.fragment = this

        getUserData()

    }

    private fun getUserData() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel.getUser()?.let {
            id = it.id ?: 0
            name = it.name ?: ""
            school = it.school ?: ""
            desc = it.description ?: ""
            photo = it.photo ?: "https://neptune74.crocodic.net/myfriend-kelasindustri/public/VqoPjXLwGHYs4TviRtT7qU0ADQtiyAr8.jpg"
        }
    }

    fun toModify() {
        context?.openActivity<ModifyActivity> {
            putExtra("id", id)
            putExtra("name", name)
            putExtra("school", school)
            putExtra("description", desc)
            putExtra("photo", photo)
            putExtra("isUser", true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
