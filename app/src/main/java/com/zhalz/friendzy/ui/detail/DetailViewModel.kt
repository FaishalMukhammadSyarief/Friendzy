package com.zhalz.friendzy.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.response.LikeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {

    private val _isFav = MutableLiveData<Boolean?>()
    val isFav = _isFav as LiveData<Boolean?>

    fun likeFriend(userID: Int ,targetID: Int) = viewModelScope.launch {
        ApiObserver.run( {apiService.like(userID, targetID)}, false,
            object : ApiObserver.ModelResponseListener<LikeResponse> {
                override suspend fun onSuccess(response: LikeResponse) {
                    super.onSuccess(response)
                    _isFav.postValue(response.liked)
                }
            })
    }

    fun getUserID() = userRepositoryImpl.getUser()?.id

}