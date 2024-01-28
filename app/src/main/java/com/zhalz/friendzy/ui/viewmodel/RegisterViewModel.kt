package com.zhalz.friendzy.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.api.ApiService
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.remote.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {

    private val _registerResponse = MutableSharedFlow<RegisterResponse>()
    val registerResponse = _registerResponse.asSharedFlow()

    fun register(name: String?, phone: String?, password: String?) = viewModelScope.launch {
        ApiObserver.run( {apiService.register(name, phone, password)}, false, object : ApiObserver.ResponseListenerFlow<RegisterResponse>(_registerResponse) {
            override suspend fun onSuccess(response: RegisterResponse) {
                super.onSuccess(response)
                response.data?.let {
                    userRepositoryImpl.insert(it)
                }
            }
        })
    }

}