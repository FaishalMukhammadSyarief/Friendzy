package com.zhalz.friendzy.data.user

import androidx.room.Dao
import androidx.room.Query
import com.crocodic.core.data.CoreDao
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao: CoreDao<UserEntity> {

    @Query("SELECT * FROM UserEntity")
    fun getUser(): Flow<List<UserEntity>>

}