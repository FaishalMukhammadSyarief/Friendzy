package com.zhalz.friendzy.api

import com.zhalz.friendzy.data.remote.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("phone") phone: String?,
        @Field("password") password: String?
    ) : LoginResponse

}