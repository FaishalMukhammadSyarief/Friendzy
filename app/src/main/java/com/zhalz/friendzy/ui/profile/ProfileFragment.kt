package com.zhalz.friendzy.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.api.ApiStatus
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    private var id = 0
    var name = ""
    var school = ""
    var desc = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.fragment = this

        getUserData()

        return binding.root
    }

    private fun getUserData() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel.getUser()?.let {
            id = it.id ?: 0
            name = it.name ?: ""
            school = it.school ?: ""
            desc = it.description ?: ""
        }
    }

    private fun updateProfile() {
        if (name.isEmpty()) binding.etName.error = getString(R.string.msg_error)
        if (school.isEmpty()) binding.etSchool.error = getString(R.string.msg_error)
        if (desc.isEmpty()) binding.etDesc.error = getString(R.string.msg_error)

        if (name.isNotEmpty() && school.isNotEmpty() && desc.isNotEmpty()) {
            viewModel.update(id, name, school, desc)

            lifecycleScope.launch {
                viewModel.updateResponse.collect {
                    it.let {
                        if (it.status == ApiStatus.LOADING) {
                            //TODO
                        } else if (it.status == ApiStatus.SUCCESS) {
                            binding.apply {
                                etName.isEnabled = false
                                etSchool.isEnabled = false
                                etDesc.isEnabled = false
                                fabUpdate.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_edit, null))
                            }
                            Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun editProfile() {
        binding.apply {
            etName.isEnabled = true
            etSchool.isEnabled = true
            etDesc.isEnabled = true
            fabUpdate.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_check, null))
        }
    }

    fun btnClick() {
        if (binding.etName.isEnabled) updateProfile()
        else editProfile()
    }

}
