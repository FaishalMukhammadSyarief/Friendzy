package com.zhalz.friendzy.data.response

import com.google.gson.annotations.SerializedName

data class RetrofitResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("liked")
	val liked: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
