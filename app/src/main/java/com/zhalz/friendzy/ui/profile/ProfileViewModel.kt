package com.zhalz.friendzy.ui.profile

import com.zhalz.friendzy.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel() {
    fun getUser() = userRepositoryImpl.getUser()
}
