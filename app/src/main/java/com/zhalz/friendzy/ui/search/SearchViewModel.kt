package com.zhalz.friendzy.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.response.ListFriendResponse
import com.zhalz.friendzy.data.user.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val _loginResponse = MutableSharedFlow<ListFriendResponse>()
    val loginResponse = _loginResponse.asSharedFlow()

    val listFiltered = MutableLiveData<List<UserEntity>?>()

    fun getListFriend(id: Int?, query: String?) = viewModelScope.launch {
        ApiObserver.run({ apiService.getListFriend(id) }, false,
            object : ApiObserver.ResponseListenerFlow<ListFriendResponse>(_loginResponse) {
                override suspend fun onSuccess(response: ListFriendResponse) {
                    super.onSuccess(response)
                    listFiltered.postValue(response.data?.filter {
                        it.name?.contains(query ?: "", true) ?: true
                    })
                }
            } )
    }

    fun getUserID() = userRepositoryImpl.getUser()?.id
}