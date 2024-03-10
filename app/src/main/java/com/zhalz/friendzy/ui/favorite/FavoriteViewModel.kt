package com.zhalz.friendzy.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.response.ListFriendResponse
import com.zhalz.friendzy.data.user.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor() : BaseViewModel() {

    private val _friendResponse = MutableSharedFlow<ListFriendResponse>()
    val listFavorite = MutableLiveData<List<UserEntity>?>()

    fun getListFriend(id: Int?) = viewModelScope.launch {
        ApiObserver.run({ apiService.getListFriend(id) }, false,
            object : ApiObserver.ResponseListenerFlow<ListFriendResponse>(_friendResponse) {
                override suspend fun onSuccess(response: ListFriendResponse) {
                    super.onSuccess(response)
                    listFavorite.postValue(response.data?.filter {
                        it.liked == true
                    })
                }
            } )
    }

    fun getUserID() = userRepositoryImpl.getUser()?.id

}