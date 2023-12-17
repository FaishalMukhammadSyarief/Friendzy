package com.zhalz.friendzy.data.friend

import kotlinx.coroutines.flow.Flow

interface FriendRepository {

    suspend fun insert(friend: FriendEntity)
    suspend fun update(friend: FriendEntity)
    suspend fun delete(friend: FriendEntity)

    suspend fun search(query: String?) : List<FriendEntity>

    fun getAll() : Flow<List<FriendEntity>>

}