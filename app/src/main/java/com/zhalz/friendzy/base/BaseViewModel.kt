package com.zhalz.friendzy.base

import com.crocodic.core.base.viewmodel.CoreViewModel
import com.zhalz.friendzy.data.repository.FriendRepository
import javax.inject.Inject

open class BaseViewModel: CoreViewModel() {

    @Inject
    lateinit var friendRepository: FriendRepository

    override fun apiLogout() {}

    override fun apiRenewToken() { }
}