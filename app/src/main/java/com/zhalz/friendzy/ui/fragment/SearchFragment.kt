package com.zhalz.friendzy.ui.fragment

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
import com.zhalz.friendzy.R
import com.zhalz.friendzy.adapter.FriendAdapter
import com.zhalz.friendzy.data.AppDatabase
import com.zhalz.friendzy.data.friend.FriendEntity
import com.zhalz.friendzy.databinding.FragmentSearchBinding
import com.zhalz.friendzy.ui.activity.DetailActivity
import com.zhalz.friendzy.ui.viewmodel.SearchFactory
import com.zhalz.friendzy.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels {
        SearchFactory(AppDatabase.getInstance(requireContext()).friendDao())
    }

    private lateinit var binding: FragmentSearchBinding

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

            override fun onQueryTextChange(inputText: String?): Boolean {
                viewModel.searchFriend(inputText)
                lifecycleScope.launch {
                    viewModel.friends.collect{
                        binding.friendAdapter = FriendAdapter(it) { data ->
                            toDetail(data)
                        }
                    }
                }
                return true
            }
        })

        return binding.root
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