package com.zhalz.friendzy.ui.main

import androidx.lifecycle.viewModelScope
import com.zhalz.friendzy.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    fun deleteUser() = viewModelScope.launch(Dispatchers.IO) {
        userRepositoryImpl.deleteUser()
    }
}