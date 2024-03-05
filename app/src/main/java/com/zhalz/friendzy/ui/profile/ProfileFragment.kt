package com.zhalz.friendzy.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

//    private var id = 0
    var name = ""
    var school = ""
    var desc = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.activity = this

        getUser()

        return binding.root
    }

    private fun getUser() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getUser().let {
//                id = it?.id ?: 0
                name = it?.name ?: "NULL"
                school = it?.school ?: "NULL"
                desc = it?.description ?: "NULL"
            }
        }
    }

    fun updateProfile() {
//        viewModel.update(id, name, school, desc)
    }

}
