package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhalz.friendzy.data.friend.FriendDao
import com.zhalz.friendzy.data.friend.FriendEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyViewModel @Inject constructor(private val friendManager: FriendDao): ViewModel() {

    fun createFriend(friend: FriendEntity){
        viewModelScope.launch {
            friendManager.insert(friend)
        }
    }

   fun editFriend(friend: FriendEntity){
        viewModelScope.launch {
            friendManager.update(friend)
        }
    }

   fun deleteFriend(friend: FriendEntity){
        viewModelScope.launch {
            friendManager.delete(friend)
        }
    }

}
