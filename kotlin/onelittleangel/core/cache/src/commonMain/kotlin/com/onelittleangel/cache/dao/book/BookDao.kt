package com.onelittleangel.cache.dao.book

import com.onelittleangel.cache.model.CachedBook
import kotlinx.coroutines.flow.Flow

interface BookDao {

    fun bookById(idBook: String): Flow<CachedBook>

    fun bookByName (name: String): Flow<CachedBook>

    fun booksByIdMovement (idMovement: String): Flow<List<CachedBook>>

    fun booksByIdTheme(idTheme: String): Flow<List<CachedBook>>

    fun bookByIdPresentation (idPresentation: String): Flow<CachedBook>

    fun bookByIdPicture(idPicture: String): Flow<CachedBook>

    fun allBooks() : Flow<List<CachedBook>>
}