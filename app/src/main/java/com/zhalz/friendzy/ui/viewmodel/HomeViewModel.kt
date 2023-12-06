package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhalz.friendzy.data.friend.FriendDao

class HomeViewModel(private val friendManager: FriendDao): ViewModel() {

    fun getFriend() = friendManager.getAll()
}

class HomeFactory(private val friendManager: FriendDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(friendManager) as T
    }
}