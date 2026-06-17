package com.onelittleangel.data.source

import com.onelittleangel.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun bookById(idBook: String): Flow<Book>

    fun bookByName (name: String): Flow<Book>

    fun booksByIdMovement (idMovement: String): Flow<List<Book>>

    fun booksByIdTheme(idTheme: String): Flow<List<Book>>

    fun bookByIdPresentation (idPresentation: String): Flow<Book>

    fun bookByIdPicture(idPicture: String): Flow<Book>

    fun allBooks() : Flow<List<Book>>
}