package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zhalz.friendzy.data.friend.FriendDao
import com.zhalz.friendzy.data.friend.FriendEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val friendManager: FriendDao): ViewModel() {

    val friends = MutableSharedFlow<List<FriendEntity>>()

    fun searchFriend(inputText: String? = null) {
        viewModelScope.launch {
            val searchedFriend = friendManager.searchFriend(inputText)
            friends.emit(searchedFriend)
        }
    }

}

class SearchFactory(private val friendManager: FriendDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(friendManager) as T
    }
}