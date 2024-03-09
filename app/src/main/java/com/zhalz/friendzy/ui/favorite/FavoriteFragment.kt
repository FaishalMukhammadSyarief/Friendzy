package com.zhalz.friendzy.ui.favorite

import android.os.Bundle
import android.view.View
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseFragment
import com.zhalz.friendzy.databinding.FragmentFavoriteBinding

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}