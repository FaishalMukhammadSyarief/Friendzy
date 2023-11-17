package com.zhalz.friendzy.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhalz.friendzy.ui.DetailActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.adapter.FriendAdapter
import com.zhalz.friendzy.data.database.AppDatabase
import com.zhalz.friendzy.data.database.FriendDao
import com.zhalz.friendzy.data.database.FriendEntity
import com.zhalz.friendzy.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val friendManager: FriendDao by lazy {
        AppDatabase.getInstance(requireContext()).friendDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.homeFragment = this

        lifecycleScope.launch {
            friendManager.getAll().collect{
                binding.friendAdapter = FriendAdapter(it) { data ->
                    toDetail(data)
                }
            }
        }

        return binding.root
    }

    fun toDetail(data: FriendEntity){
        val toDetail = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("name", data.name)
            putExtra("birth", data.birth)
            putExtra("description", data.description)
            putExtra("id", data.id)
        }
        startActivity(toDetail)
    }

}