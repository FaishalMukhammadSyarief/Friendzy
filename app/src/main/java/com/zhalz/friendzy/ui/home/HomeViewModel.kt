package com.zhalz.friendzy.ui.home

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.response.ListFriendResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel() {

    private val _friendResponse = MutableSharedFlow<ListFriendResponse>()
    val friendResponse = _friendResponse.asSharedFlow()

    fun getListFriend(id: Int?) = viewModelScope.launch {
        ApiObserver.run({ apiService.getListFriend(id) }, false,
            object : ApiObserver.ResponseListenerFlow<ListFriendResponse>(_friendResponse) {} )
    }

    fun getUserID() = userRepositoryImpl.getUser()?.id

}