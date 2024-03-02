package com.zhalz.friendzy.api

import com.zhalz.friendzy.data.response.LoginResponse
import com.zhalz.friendzy.data.response.RegisterResponse
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

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field ("name") name: String?,
        @Field ("phone") phone: String?,
        @Field ("password") password: String?
    ) : RegisterResponse

}