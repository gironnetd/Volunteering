package com.onelittleangel.data.source

import com.onelittleangel.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    //var user: CurrentValueSubject<CachedUser, Error>! { get set }

    fun currentUser(): Flow<User>

    suspend fun saveOrUpdate(currentUser: User)

    suspend fun deleteCurrentUser()
}