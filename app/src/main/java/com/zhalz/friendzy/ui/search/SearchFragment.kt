package com.zhalz.friendzy.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.zhalz.friendzy.R
import com.zhalz.friendzy.data.user.UserEntity
import com.zhalz.friendzy.databinding.FragmentSearchBinding
import com.zhalz.friendzy.databinding.ItemFriendsBinding
import com.zhalz.friendzy.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    private val adapter by lazy {
        ReactiveListAdapter<ItemFriendsBinding, UserEntity>(R.layout.item_friends).initItem { _, data ->
            toDetail(data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.fragment = this

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                searchFriend(query)
                return true
            }
        })

        return binding.root
    }

    private fun searchFriend(query: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getUserID().let {
                viewModel.getListFriend(it, query)
            }
        }
        viewModel.listFiltered.observe(viewLifecycleOwner) { adapter.submitList(it) }
        binding.rvQueryFriend.adapter = adapter
    }

    private fun toDetail(data: UserEntity) {
        val toDetail = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("id", data.id)
            putExtra("name", data.name)
            putExtra("school", data.school)
            putExtra("description", data.description)
            putExtra("liked", data.liked)
        }
        startActivity(toDetail)
    }

}