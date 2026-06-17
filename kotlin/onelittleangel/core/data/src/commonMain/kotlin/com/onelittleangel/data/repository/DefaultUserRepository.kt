package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.user.UserDao
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.UserRepository
import com.onelittleangel.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultUserRepository(private val userDao: UserDao) : UserRepository {

    override fun currentUser(): Flow<User> {
        return  userDao.currentUser().map { it.asExternalModel() }
    }

    override suspend fun saveOrUpdate(currentUser: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCurrentUser() {
        TODO("Not yet implemented")
    }
}