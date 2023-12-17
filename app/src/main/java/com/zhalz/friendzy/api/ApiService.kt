package com.zhalz.friendzy.api

import com.crocodic.core.api.ModelResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("Login")
    suspend fun login(
        @Field("phone") phone: String?,
        @Field("password") password: String?
    ) : ModelResponse

}