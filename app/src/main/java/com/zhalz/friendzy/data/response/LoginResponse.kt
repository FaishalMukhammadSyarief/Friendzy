package com.zhalz.friendzy.data.response

import com.crocodic.core.api.ModelResponse
import com.zhalz.friendzy.data.user.UserEntity

data class LoginResponse(
    val data: UserEntity?
) : ModelResponse()