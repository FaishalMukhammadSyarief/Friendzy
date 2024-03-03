package com.zhalz.friendzy.ui.search

import com.zhalz.friendzy.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel() {
    fun searchFriend(query: String?) = friendRepositoryImpl.searchFriend(query)
}