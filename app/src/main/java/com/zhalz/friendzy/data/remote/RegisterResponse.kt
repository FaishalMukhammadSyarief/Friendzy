package com.zhalz.friendzy.data.remote

import com.crocodic.core.api.ModelResponse
import com.zhalz.friendzy.data.user.UserEntity

data class RegisterResponse (
    val data: UserEntity?
) : ModelResponse()