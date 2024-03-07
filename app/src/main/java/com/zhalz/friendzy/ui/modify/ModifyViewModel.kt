package com.zhalz.friendzy.ui.modify

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.friend.FriendEntity
import com.zhalz.friendzy.data.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyViewModel @Inject constructor(): BaseViewModel() {

    private val _updateResponse = MutableSharedFlow<LoginResponse>()
    val updateResponse = _updateResponse.asSharedFlow()

    fun update(id: Int?, name: String?, school: String, desc: String?) {
        viewModelScope.launch {
            ApiObserver.run({ apiService.update(id, name, school, desc) }, false,
                object : ApiObserver.ResponseListenerFlow<LoginResponse>(_updateResponse) {
                    override suspend fun onSuccess(response: LoginResponse) {
                        super.onSuccess(response)
                        response.data?.let { userRepositoryImpl.insert(it) }
                    }
                })
        }
    }

    fun createFriend(friend: FriendEntity){
        viewModelScope.launch {
            friendRepositoryImpl.insert(friend)
        }
    }

   fun editFriend(friend: FriendEntity){
        viewModelScope.launch {
            friendRepositoryImpl.update(friend)
        }
    }

   fun deleteFriend(friend: FriendEntity){
        viewModelScope.launch {
            friendRepositoryImpl.delete(friend)
        }
    }

}
