package com.zhalz.friendzy.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.tos
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseFragment
import com.zhalz.friendzy.databinding.FragmentProfileBinding
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
            photo = it.photo ?: ""
        }
    }

    private fun updateProfile() {
        if (name.isEmpty()) binding?.etName?.error = getString(R.string.msg_error)
        if (school.isEmpty()) binding?.etSchool?.error = getString(R.string.msg_error)
        if (desc.isEmpty()) binding?.etDesc?.error = getString(R.string.msg_error)

        if (name.isNotEmpty() && school.isNotEmpty() && desc.isNotEmpty()) {
            viewModel.update(id, name, school, desc)

            lifecycleScope.launch {
                viewModel.updateResponse.collect {
                    it.let {
                        if (it.status == ApiStatus.LOADING) loadingDialog?.show("Updating...")
                        else if (it.status == ApiStatus.SUCCESS) {
                            loadingDialog?.dismiss()
                            binding?.isEdit = false
                            context?.tos("Profile Updated Successfully")
                        }
                    }
                }
            }
        }
    }

    private fun editProfile() {
        binding?.isEdit = true
    }

    fun btnClick() {
        if (binding?.isEdit == true) updateProfile()
        else editProfile()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
