package com.zhalz.friendzy.data.repository

import com.zhalz.friendzy.data.friend.FriendDao
import com.zhalz.friendzy.data.friend.FriendEntity
import javax.inject.Inject

class FriendRepository @Inject constructor(private val friendDao: FriendDao) {

    suspend fun insert(friend: FriendEntity) = friendDao.insert(friend)

    suspend fun update(friend: FriendEntity) = friendDao.update(friend)

    suspend fun delete(friend: FriendEntity) = friendDao.delete(friend)

    fun getAll() = friendDao.getAll()

    suspend fun search(query: String?) = friendDao.searchFriend(query)

}