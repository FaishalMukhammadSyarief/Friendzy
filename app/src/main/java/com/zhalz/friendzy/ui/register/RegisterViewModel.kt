package com.zhalz.friendzy.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.base.BaseViewModel
import com.zhalz.friendzy.data.response.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : BaseViewModel() {

    private val _registerResponse = MutableSharedFlow<RegisterResponse>()
    val registerResponse = _registerResponse.asSharedFlow()

    val message = MutableLiveData<String>()

    fun register(name: String?, phone: String?, password: String?) = viewModelScope.launch {
        try {
            ApiObserver.run( {apiService.register(name, phone, password)}, false, object : ApiObserver.ResponseListenerFlow<RegisterResponse>(_registerResponse) {
                override suspend fun onSuccess(response: RegisterResponse) {
                    super.onSuccess(response)
                    response.data?.let {
                        userRepositoryImpl.insert(it)
                    }
                }
            })
        } catch (e: Exception) {
            message.value = "The phone has already been taken."
        }
    }

}