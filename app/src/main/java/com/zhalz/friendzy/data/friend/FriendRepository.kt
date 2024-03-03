package com.zhalz.friendzy.data.friend

import kotlinx.coroutines.flow.Flow

interface FriendRepository {

    suspend fun insert(friend: FriendEntity)
    suspend fun update(friend: FriendEntity)
    suspend fun delete(friend: FriendEntity)

    fun searchFriend(query: String?) : Flow<List<FriendEntity>>
    fun getAll() : Flow<List<FriendEntity>>

}