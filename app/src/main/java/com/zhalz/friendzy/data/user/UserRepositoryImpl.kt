package com.zhalz.friendzy.data.user

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao): UserRepository {

    override suspend fun insert(user: UserEntity) = userDao.insert(user.copy(databaseId = 1))
    override suspend fun deleteUser() = userDao.deleteUser()
    override fun getUser() = userDao.getUser()
    override suspend fun checkLogin(): Boolean {
        val isLogin = userDao.getUser()
        return isLogin != null
    }

}