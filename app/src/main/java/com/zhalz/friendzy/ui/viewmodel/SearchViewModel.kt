package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhalz.friendzy.data.friend.FriendDao
import com.zhalz.friendzy.data.friend.FriendEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val friendManager: FriendDao): ViewModel() {

    val friends = MutableSharedFlow<List<FriendEntity>>()

    fun searchFriend(inputText: String? = null) {
        viewModelScope.launch {
            val searchedFriend = friendManager.searchFriend(inputText)
            friends.emit(searchedFriend)
        }
    }

}