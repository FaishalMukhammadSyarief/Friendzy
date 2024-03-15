package com.zhalz.friendzy.ui.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseFragment
import com.zhalz.friendzy.data.user.UserEntity
import com.zhalz.friendzy.databinding.FragmentSearchBinding
import com.zhalz.friendzy.databinding.ItemFriendsBinding
import com.zhalz.friendzy.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private val adapter by lazy {
        ReactiveListAdapter<ItemFriendsBinding, UserEntity>(R.layout.item_friends).initItem { _, data -> toDetail(data) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.fragment = this

        binding?.adapter = adapter

        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                searchFriend(query)
                return true
            }
        })
    }

    private fun searchFriend(query: String?) {
        val handler = Handler(Looper.getMainLooper())
        val searchFriend = Runnable {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getUserID().let {
                    viewModel.getListFriend(it, query)
                }
            }
        }
        handler.removeCallbacks(searchFriend)
        handler.postDelayed(searchFriend, 1_000)

        viewModel.listFiltered.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    private fun toDetail(data: UserEntity) {
        context?.openActivity<DetailActivity> {
            putExtra("id", data.id)
            putExtra("name", data.name)
            putExtra("school", data.school)
            putExtra("description", data.description)
            putExtra("photo", data.photo ?: "https://neptune74.crocodic.net/myfriend-kelasindustri/public/VqoPjXLwGHYs4TviRtT7qU0ADQtiyAr8.jpg")
            putExtra("liked", data.liked)
        }
    }

}