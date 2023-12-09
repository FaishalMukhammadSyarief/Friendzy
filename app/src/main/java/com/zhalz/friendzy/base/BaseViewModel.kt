package com.zhalz.friendzy.base

import com.crocodic.core.base.viewmodel.CoreViewModel
import com.zhalz.friendzy.data.friend.FriendDao
import javax.inject.Inject

open class BaseViewModel: CoreViewModel() {

    @Inject
    lateinit var friendManager: FriendDao

    override fun apiLogout() {}

    override fun apiRenewToken() { }
}