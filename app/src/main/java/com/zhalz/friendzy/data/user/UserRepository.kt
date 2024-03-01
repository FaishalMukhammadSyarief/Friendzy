package com.zhalz.friendzy.data.user

interface UserRepository {

    suspend fun insert(user: UserEntity)
    suspend fun deleteUser()
    fun getUser(): UserEntity?
    suspend fun checkLogin(): Boolean

}