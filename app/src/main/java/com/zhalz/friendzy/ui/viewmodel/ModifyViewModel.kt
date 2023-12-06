package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zhalz.friendzy.data.friend.FriendDao
import com.zhalz.friendzy.data.friend.FriendEntity
import kotlinx.coroutines.launch

class ModifyViewModel(private val friendManager: FriendDao): ViewModel() {

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

class Factory(private val friendManager: FriendDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ModifyViewModel(friendManager) as T
    }
}
