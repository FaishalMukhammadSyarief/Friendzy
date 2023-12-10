package com.zhalz.friendzy.base

import com.crocodic.core.base.viewmodel.CoreViewModel
import com.zhalz.friendzy.data.repository.FriendRepositoryImpl
import javax.inject.Inject

open class BaseViewModel: CoreViewModel() {

    @Inject
    lateinit var friendRepositoryImpl: FriendRepositoryImpl

    override fun apiLogout() {}

    override fun apiRenewToken() { }
}