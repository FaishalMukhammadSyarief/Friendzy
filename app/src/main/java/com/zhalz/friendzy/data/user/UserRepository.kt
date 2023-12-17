package com.zhalz.friendzy.data.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insert(user: UserEntity)
    suspend fun update(user: UserEntity)
    suspend fun delete(user: UserEntity)

    fun getUser(): Flow<List<UserEntity>>
}