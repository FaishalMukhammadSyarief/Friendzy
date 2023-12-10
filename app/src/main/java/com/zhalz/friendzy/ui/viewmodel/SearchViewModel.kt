package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.friend.FriendEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): BaseViewModel() {

    val friends = MutableSharedFlow<List<FriendEntity>>()

    fun searchFriend(inputText: String? = null) {
        viewModelScope.launch {
            val searchedFriend = friendRepository.search(inputText)
            friends.emit(searchedFriend)
        }
    }

}