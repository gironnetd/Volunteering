package com.onelittleangel.data.source

import com.onelittleangel.model.Author
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {

    fun authorById(idAuthor: String): Flow<Author>

    fun authorsByName(name: String): Flow<Author>

    fun authorsByIdFaith(idFaith: String): Flow<List<Author>>

    fun authorsByIdTheme(idTheme: String): Flow<List<Author>>

    fun authorByIdBiography(idBiography: String): Flow<List<Author>>

    fun authorByIdPicture(idPicture: String): Flow<List<Author>>

    fun allAuthors(): Flow<List<Author>>
}