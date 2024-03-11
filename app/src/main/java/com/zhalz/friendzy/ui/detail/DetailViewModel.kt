package com.zhalz.friendzy.ui.detail

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.response.LikeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(): BaseViewModel() {

    private val _likeResponse = MutableSharedFlow<LikeResponse>()
    val likeResponse = _likeResponse.asSharedFlow()

    fun likeFriend(userID: Int? ,targetID: Int?) = viewModelScope.launch {
        ApiObserver.run( {apiService.like(userID, targetID)}, false,
            object : ApiObserver.ResponseListenerFlow<LikeResponse>(_likeResponse){

            })
    }

    fun getUserID() = userRepositoryImpl.getUser()?.id

}