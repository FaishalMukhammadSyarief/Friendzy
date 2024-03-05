package com.zhalz.friendzy.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.zhalz.friendzy.ui.detail.DetailActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.data.friend.FriendEntity
import com.zhalz.friendzy.data.user.UserEntity
import com.zhalz.friendzy.databinding.FragmentHomeBinding
import com.zhalz.friendzy.databinding.ItemCarouselBinding
import com.zhalz.friendzy.databinding.ItemFriendsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeFragment = this

        getFriend()
        setCarousel()

        return binding.root
    }

    private fun getFriend() {
        val adapter =
            ReactiveListAdapter<ItemFriendsBinding, UserEntity>(R.layout.item_friends).initItem { _, _ -> }
        lifecycleScope.launch {
            viewModel.listFriend.collect { adapter.submitList(it.data) }
        }
        binding.rvFriend.adapter = adapter
    }

    private fun setCarousel() {
        val adapter =
            ReactiveListAdapter<ItemCarouselBinding, UserEntity>(R.layout.item_carousel).initItem { _, _ -> }
        lifecycleScope.launch {
            viewModel.listFriend.collect {
                val listFriend = it.data
                adapter.submitList(
                    listFriend?.subList(listFriend.size - 3, listFriend.size)?.reversed()
                )
            }
        }

        binding.rvCarousel.adapter = adapter
        CarouselLayoutManager(HeroCarouselStrategy())
        CarouselSnapHelper().attachToRecyclerView(binding.rvCarousel)

    }

    override fun onStart() {
        super.onStart()
        viewModel.getListFriend()
    }

    private fun toDetail(data: FriendEntity) {
        val toDetail = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("name", data.name)
            putExtra("birth", data.birth)
            putExtra("description", data.description)
            putExtra("photo", data.photo)
            putExtra("id", data.id)
        }
        startActivity(toDetail)
    }

}