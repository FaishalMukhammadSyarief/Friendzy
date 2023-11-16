package com.zhalz.friendzy.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.zhalz.friendzy.R
import com.zhalz.friendzy.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.btnSpecialScreen.setOnClickListener {
            navigateToSpecialScreen()
        }

        return binding.root
    }

    private fun navigateToSpecialScreen() {
        findNavController().navigate(R.id.action_profileFragment_to_specialFragment)
    }


}