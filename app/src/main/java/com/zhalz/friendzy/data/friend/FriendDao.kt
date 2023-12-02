package com.zhalz.friendzy.data.friend

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {
    @Insert
    suspend fun insert(friend: FriendEntity)

    @Update
    suspend fun update(friend: FriendEntity)

    @Delete
    suspend fun delete(friend: FriendEntity)

    @Query("SELECT * FROM FriendEntity")
    fun getAll(): Flow<List<FriendEntity>>

    @Query("SELECT * FROM FriendEntity WHERE name LIKE :keyword")
    suspend fun findFriend(keyword: String): List<FriendEntity>

    suspend fun searchFriend(keyword: String?): List<FriendEntity> {
        return findFriend("%$keyword%")
    }

}