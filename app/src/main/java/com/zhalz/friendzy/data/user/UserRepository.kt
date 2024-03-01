package com.zhalz.friendzy.data.user

interface UserRepository {

    suspend fun insert(user: UserEntity)

    fun getUser(): UserEntity?
}