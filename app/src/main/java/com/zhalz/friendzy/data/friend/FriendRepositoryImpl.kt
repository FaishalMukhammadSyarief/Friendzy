package com.zhalz.friendzy.data.friend

import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(private val friendDao: FriendDao): FriendRepository {

    override suspend fun insert(friend: FriendEntity) = friendDao.insert(friend)
    override suspend fun update(friend: FriendEntity) = friendDao.update(friend)
    override suspend fun delete(friend: FriendEntity) = friendDao.delete(friend)

    override suspend fun search(query: String?) = friendDao.searchFriend(query)

    override fun getAll() = friendDao.getAll()

}