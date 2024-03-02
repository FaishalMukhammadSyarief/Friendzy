package com.zhalz.friendzy.ui.welcome

import com.zhalz.friendzy.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(): BaseViewModel() {
    suspend fun checkLogin(): Boolean {
        return userRepositoryImpl.checkLogin()
    }
}