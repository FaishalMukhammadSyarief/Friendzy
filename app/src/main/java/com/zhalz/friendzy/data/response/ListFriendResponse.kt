package com.zhalz.friendzy.data.response

import com.crocodic.core.api.ModelResponse
import com.google.gson.annotations.SerializedName
import com.zhalz.friendzy.data.user.UserEntity

data class ListFriendResponse(
    @field:SerializedName("data")
    val data: List<UserEntity>?
) : ModelResponse()