package com.zhalz.friendzy.data.user

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao): UserRepository {
    override suspend fun insert(user: UserEntity) = userDao.insert(user)
    override suspend fun update(user: UserEntity) = userDao.update(user)
    override suspend fun delete(user: UserEntity) = userDao.delete(user)

    override fun getUser(): Flow<List<UserEntity>> = userDao.getUser()
}