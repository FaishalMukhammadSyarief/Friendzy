package com.zhalz.friendzy.data.user

import androidx.room.Dao
import androidx.room.Query
import com.crocodic.core.data.CoreDao

@Dao
interface UserDao: CoreDao<UserEntity> {

    @Query("SELECT * FROM UserEntity WHERE databaseId = 1")
    fun getUser(): UserEntity?

    @Query("DELETE FROM UserEntity WHERE databaseId = 1")
    suspend fun deleteUser()

}