package com.zhalz.friendzy.data.response

import com.crocodic.core.api.ModelResponse
import com.google.gson.annotations.SerializedName

data class LikeResponse (

    @field:SerializedName("liked")
    val liked: Boolean? = null,

): ModelResponse()