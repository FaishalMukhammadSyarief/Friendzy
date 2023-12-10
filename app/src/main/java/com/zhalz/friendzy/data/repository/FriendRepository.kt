package com.zhalz.friendzy.data.repository

import com.zhalz.friendzy.data.AppDatabase
import com.zhalz.friendzy.data.friend.FriendEntity
import javax.inject.Inject

class FriendRepository @Inject constructor(private val database: AppDatabase) {

    suspend fun insert(friend: FriendEntity) = database.friendDao().insert(friend)

    suspend fun update(friend: FriendEntity) = database.friendDao().update(friend)

    suspend fun delete(friend: FriendEntity) = database.friendDao().delete(friend)

    fun getAll() = database.friendDao().getAll()

    suspend fun search(query: String?) = database.friendDao().searchFriend(query)

}