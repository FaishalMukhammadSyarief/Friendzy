package com.zhalz.friendzy.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiStatus
import com.zhalz.friendzy.api.ApiConfig
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.response.LikeResponse
import com.zhalz.friendzy.data.response.RetrofitResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {

/*    private val _likeResponse = MutableSharedFlow<LikeResponse>()
    val likeResponse = _likeResponse.asSharedFlow()

    fun likeFriendC(userID: Int? ,targetID: Int?) = viewModelScope.launch {
        ApiObserver.run( {apiService.like(userID, targetID)}, false,
            object : ApiObserver.ResponseListenerFlow<LikeResponse>(_likeResponse) {
                override suspend fun onSuccess(response: LikeResponse) {
                    super.onSuccess(response)
                    if (response.status == ApiStatus.SUCCESS) _message.value = "SUCCESS JIR LAH"
                    else _message.value = "FAILED JIR LAH"
                }
            })
    }

    fun getUserID() = userRepositoryImpl.getUser()?.id*/

    private val _message = MutableLiveData<String>()
    val message = _message

    fun likeFriend(userID: Int?, targetID: Int?) {
        val client = ApiConfig.getApiService().likeRetrofit(userID, targetID)

        client.enqueue(object : Callback<RetrofitResponse> {

            override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                if (response.isSuccessful) _message.value = "SUCCESS"
                else _message.value = "FAILED"

            }

            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                _message.value = "FAILED"
            }

        })
    }

}