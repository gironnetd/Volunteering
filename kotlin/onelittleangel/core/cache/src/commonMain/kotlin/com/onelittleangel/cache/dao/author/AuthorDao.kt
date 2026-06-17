package com.onelittleangel.cache.dao.author

import com.onelittleangel.cache.model.CachedAuthor
import kotlinx.coroutines.flow.Flow

interface AuthorDao {

    fun authorById(idAuthor: String): Flow<CachedAuthor>

    fun authorByName(name: String): Flow<CachedAuthor>

    fun authorsByIdMovement(idMovement: String): Flow<List<CachedAuthor>>

    fun authorsByIdTheme(idTheme: String): Flow<List<CachedAuthor>>

    fun authorByIdPresentation(idPresentation: String): Flow<List<CachedAuthor>>

    fun authorByIdPicture(idPicture: String): Flow<CachedAuthor>

    fun allAuthors(): Flow<List<CachedAuthor>>
}