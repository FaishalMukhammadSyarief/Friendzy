package com.zhalz.friendzy.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.openActivity
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.zhalz.friendzy.ui.detail.DetailActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseFragment
import com.zhalz.friendzy.data.user.UserEntity
import com.zhalz.friendzy.databinding.FragmentHomeBinding
import com.zhalz.friendzy.databinding.ItemCarouselBinding
import com.zhalz.friendzy.databinding.ItemFriendsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val listAdapter by lazy {
        ReactiveListAdapter<ItemFriendsBinding, UserEntity>(R.layout.item_friends).initItem { _, data -> toDetail(data) }
    }
    private val carouselAdapter by lazy {
        ReactiveListAdapter<ItemCarouselBinding, UserEntity>(R.layout.item_carousel).initItem { _, data -> toDetail(data) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.listAdapter = listAdapter
        binding?.carouselAdapter = carouselAdapter

        setFriendList()
        setCarousel()
    }

    private fun getFriend() = lifecycleScope.launch(Dispatchers.IO) {
        viewModel.getUserID().let {
            viewModel.getListFriend(it)
        }
    }

    private fun setFriendList() = lifecycleScope.launch {
        viewModel.friendResponse.collect { listAdapter.submitList(it.data) }
    }


    private fun setCarousel() {
        lifecycleScope.launch {
            viewModel.friendResponse.collect {
                val listFriend = it.data
                carouselAdapter.submitList(
                    listFriend?.subList(listFriend.size - 3, listFriend.size)?.reversed()
                )
            }
        }
        CarouselLayoutManager(HeroCarouselStrategy())
        CarouselSnapHelper().attachToRecyclerView(binding?.rvCarousel)
    }

    private fun toDetail(data: UserEntity) {
        context?.openActivity<DetailActivity> {
            putExtra("id", data.id)
            putExtra("name", data.name)
            putExtra("school", data.school)
            putExtra("description", data.description)
            putExtra("photo", data.photo)
            putExtra("liked", data.liked)
        }
    }

    override fun onStart() {
        super.onStart()
        getFriend()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}