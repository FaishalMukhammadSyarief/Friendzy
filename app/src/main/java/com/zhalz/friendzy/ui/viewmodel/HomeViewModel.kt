package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.zhalz.friendzy.data.friend.FriendDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val friendManager: FriendDao): ViewModel() {
    fun getFriend() = friendManager.getAll()
}