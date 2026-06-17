package com.onelittleangel.cache.dao.faith

import com.onelittleangel.cache.model.CachedMovement
import kotlinx.coroutines.flow.Flow

interface FaithDao {

    fun faithById(idBiography: String): Flow<CachedMovement>

    fun faithByName(name: String): Flow<CachedMovement>

    fun faithsByIdParent(idParent: String): Flow<List<CachedMovement>>

    fun mainFaiths(): Flow<List<CachedMovement>>

    fun allFaiths(): Flow<List<CachedMovement>>
}