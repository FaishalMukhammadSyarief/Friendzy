package com.zhalz.friendzy.ui.viewmodel

import com.zhalz.friendzy.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel() {
    fun getFriend() = friendRepositoryImpl.getAll()
}