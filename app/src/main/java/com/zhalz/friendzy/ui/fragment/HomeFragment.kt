package com.zhalz.friendzy.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.zhalz.friendzy.ui.activity.DetailActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.adapter.CarouselAdapter
import com.zhalz.friendzy.adapter.FriendAdapter
import com.zhalz.friendzy.data.AppDatabase
import com.zhalz.friendzy.data.friend.FriendEntity
import com.zhalz.friendzy.databinding.FragmentHomeBinding
import com.zhalz.friendzy.ui.viewmodel.HomeFactory
import com.zhalz.friendzy.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        HomeFactory(AppDatabase.getInstance(requireContext()).friendDao())
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeFragment = this

        readFriend()
        setCarousel()

        return binding.root
    }

    private fun readFriend() {
        lifecycleScope.launch {
            viewModel.getFriend().collect{
                binding.friendAdapter = FriendAdapter(it){ data ->
                    toDetail(data)
                }
            }
        }
    }

    private fun setCarousel() {
        lifecycleScope.launch {
            viewModel.getFriend().collect{
                binding.carouselAdapter = CarouselAdapter(it){ data ->
                    toDetail(data)
                }
            }
        }

        binding.rvCarousel.layoutManager = CarouselLayoutManager()
        CarouselLayoutManager(HeroCarouselStrategy())
        CarouselSnapHelper().attachToRecyclerView(binding.rvCarousel)
    }

    private fun toDetail(data: FriendEntity){
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