package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.friend.FriendEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyViewModel @Inject constructor(): BaseViewModel() {

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
