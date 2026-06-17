package com.onelittleangel.cache.dao.user

import com.onelittleangel.cache.model.CachedUser
import kotlinx.coroutines.flow.Flow

interface UserDao {

    //var user: CurrentValueSubject<CachedUser, Error>! { get set }

    fun currentUser(): Flow<CachedUser>

    suspend fun saveOrUpdate(currentUser: CachedUser)

    suspend fun deleteCurrentUser()
}