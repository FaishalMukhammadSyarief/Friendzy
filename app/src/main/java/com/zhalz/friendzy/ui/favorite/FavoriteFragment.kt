package com.zhalz.friendzy.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.base.adapter.ReactiveListAdapter
import com.crocodic.core.extension.openActivity
import com.zhalz.friendzy.R
import com.zhalz.friendzy.base.BaseFragment
import com.zhalz.friendzy.data.user.UserEntity
import com.zhalz.friendzy.databinding.FragmentFavoriteBinding
import com.zhalz.friendzy.databinding.ItemFriendsBinding
import com.zhalz.friendzy.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter by lazy {
        ReactiveListAdapter<ItemFriendsBinding, UserEntity>(R.layout.item_friends).initItem { _, data -> toDetail(data) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.adapter = adapter
    }

    private fun getFavorite() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getUserID().let {
                viewModel.getListFriend(it)
            }
        }
        viewModel.listFavorite.observe(viewLifecycleOwner) { adapter.submitList(it) }
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

    override fun onStart() {
        super.onStart()
        getFavorite()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}