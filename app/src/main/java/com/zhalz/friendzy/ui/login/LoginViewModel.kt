package com.zhalz.friendzy.ui.login

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.zhalz.friendzy.data.response.LoginResponse
import com.zhalz.friendzy.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private val _loginResponse = MutableSharedFlow<LoginResponse>()
    val loginResponse = _loginResponse.asSharedFlow()

    fun login(phone: String?, password: String?) = viewModelScope.launch {
        ApiObserver.run( { apiService.login(phone, password)}, false, object : ApiObserver.ResponseListenerFlow<LoginResponse>(_loginResponse){
            override suspend fun onSuccess(response: LoginResponse) {
                super.onSuccess(response)
                response.data?.let {
                    userRepositoryImpl.insert(it)
                }
            }
        })
    }

}