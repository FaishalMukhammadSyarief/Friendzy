package com.zhalz.friendzy.data.friend

import androidx.room.Dao
import androidx.room.Query
import com.crocodic.core.data.CoreDao
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao: CoreDao<FriendEntity> {

    @Query("SELECT * FROM FriendEntity")
    fun getAll(): Flow<List<FriendEntity>>

    @Query("SELECT * FROM FriendEntity WHERE name LIKE :query")
    fun searchFriend(query: String?): Flow<List<FriendEntity>>

}