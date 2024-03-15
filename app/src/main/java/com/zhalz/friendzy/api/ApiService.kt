package com.zhalz.friendzy.api

import com.zhalz.friendzy.data.response.LikeResponse
import com.zhalz.friendzy.data.response.ListFriendResponse
import com.zhalz.friendzy.data.response.LoginResponse
import com.zhalz.friendzy.data.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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

    @Multipart
    @POST("update-profile")
    suspend fun update(
        @Query("id_user") id: Int?,
        @Query("name") name: String?,
        @Query("school") school: String?,
        @Query("description") description: String?,
        @Part photo: MultipartBody.Part?
    ): LoginResponse

    @FormUrlEncoded
    @POST("update-profile")
    suspend fun updateNoPhoto(
        @Field("id_user") id: Int?,
        @Field("name") name: String?,
        @Field("school") school: String?,
        @Field("description") description: String?
    ): LoginResponse

    @GET("get-list-friends")
    suspend fun getListFriend(
        @Query ("users_id") id: Int?
    ): ListFriendResponse

    @FormUrlEncoded
    @POST("like")
    suspend fun like(
        @Field ("users_id") userID: Int?,
        @Field ("user_id_i_like") targetID: Int?,
    ) : LikeResponse

}