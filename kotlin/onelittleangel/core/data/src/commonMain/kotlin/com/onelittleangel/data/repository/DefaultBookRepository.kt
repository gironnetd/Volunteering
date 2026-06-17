package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.book.BookDao
import com.onelittleangel.cache.model.CachedBook
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.BookRepository
import com.onelittleangel.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(private val bookDao: BookDao) : BookRepository {

    override fun bookById(idBook: String): Flow<Book> {
        return bookDao.bookById(idBook).map { it.asExternalModel() }
    }

    override fun bookByName(name: String): Flow<Book> {
        return bookDao.bookByName(name).map { it.asExternalModel() }
    }

    override fun booksByIdMovement(idMovement: String): Flow<List<Book>> {
        return bookDao.booksByIdMovement(idMovement).map { it.map(CachedBook::asExternalModel) }
    }

    override fun booksByIdTheme(idTheme: String): Flow<List<Book>> {
        return bookDao.booksByIdTheme(idTheme).map { it.map(CachedBook::asExternalModel) }
    }

    override fun bookByIdPresentation(idPresentation: String): Flow<Book> {
        return bookDao.bookByIdPresentation(idPresentation).map { it.asExternalModel() }
    }

    override fun bookByIdPicture(idPicture: String): Flow<Book> {
        return bookDao.bookByIdPicture(idPicture).map { it.asExternalModel() }
    }

    override fun allBooks(): Flow<List<Book>> {
        return bookDao.allBooks().map { it.map(CachedBook::asExternalModel) }
    }
}