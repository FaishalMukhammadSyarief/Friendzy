package com.zhalz.friendzy.data.user

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao): UserRepository {

    override suspend fun insert(user: UserEntity) = userDao.insert(user.copy(databaseId = 1))
    override fun getUser() = userDao.getUser()

}