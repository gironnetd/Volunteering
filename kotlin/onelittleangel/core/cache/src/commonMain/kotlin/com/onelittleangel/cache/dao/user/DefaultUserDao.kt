package com.onelittleangel.cache.dao.user

import com.onelittleangel.cache.model.CachedUrl
import com.onelittleangel.cache.model.CachedUser
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserDao(private val realm: Realm) : UserDao {

    override fun currentUser(): Flow<CachedUser> = flow {
        realm.query(CachedUser::class)
    }

    override suspend fun saveOrUpdate(currentUser: CachedUser) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCurrentUser() {
        TODO("Not yet implemented")
    }
}