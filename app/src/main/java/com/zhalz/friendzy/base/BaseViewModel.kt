package com.zhalz.friendzy.base

import com.crocodic.core.base.viewmodel.CoreViewModel
import com.zhalz.friendzy.api.ApiService
import com.zhalz.friendzy.data.friend.FriendRepositoryImpl
import com.zhalz.friendzy.data.user.UserRepositoryImpl
import javax.inject.Inject

open class BaseViewModel: CoreViewModel() {

    @Inject
    lateinit var friendRepositoryImpl: FriendRepositoryImpl

    @Inject
    lateinit var userRepositoryImpl: UserRepositoryImpl

    @Inject
    lateinit var apiService: ApiService

    override fun apiLogout() {}

    override fun apiRenewToken() { }
}