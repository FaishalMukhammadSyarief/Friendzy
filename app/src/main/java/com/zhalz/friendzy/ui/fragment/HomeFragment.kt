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
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.zhalz.friendzy.ui.activity.DetailActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.adapter.CarouselAdapter
import com.zhalz.friendzy.adapter.FriendAdapter
import com.zhalz.friendzy.data.friend.FriendEntity
import com.zhalz.friendzy.databinding.FragmentHomeBinding
import com.zhalz.friendzy.ui.viewmodel.HomeViewModel
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

        readFriend()
        setCarousel()

        return binding.root
    }

    private fun readFriend() {
        lifecycleScope.launch {
            viewModel.getFriend().collect {
                println(it)
                println("FAISHAAAAAAAAAAAAAL")
                binding.friendAdapter = FriendAdapter(it){ data ->
                    toDetail(data)
                }
            }
        }
    }

    private fun setCarousel() {
        lifecycleScope.launch {
            viewModel.getFriend().collect {
                var list = it

                if (it.size >= 3) {
                    list = it.subList(it.size - 3, it.size).reversed()
                }

                binding.carouselAdapter = CarouselAdapter(list){ data ->
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