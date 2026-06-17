package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.author.AuthorDao
import com.onelittleangel.cache.model.CachedAuthor
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.AuthorRepository
import com.onelittleangel.model.Author
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultAuthorRepository(private val authorDao: AuthorDao) : AuthorRepository {

    override fun authorById(idAuthor: String): Flow<Author> {
        return authorDao.authorById(idAuthor).map { it.asExternalModel() }
    }

    override fun authorsByName(name: String): Flow<Author> {
        return authorDao.authorByName(name).map { it.asExternalModel() }
    }

    override fun authorsByIdFaith(idFaith: String): Flow<List<Author>> {
        return authorDao.authorsByIdMovement(idFaith).map { it.map(CachedAuthor::asExternalModel) }
    }

    override fun authorsByIdTheme(idTheme: String): Flow<List<Author>> {
        return authorDao.authorsByIdTheme(idTheme).map { it.map(CachedAuthor::asExternalModel) }
    }

    override fun authorByIdBiography(idBiography: String): Flow<List<Author>> {
        return authorDao.authorByIdPresentation(idBiography).map { it.map(CachedAuthor::asExternalModel) }
    }

    override fun authorByIdPicture(idPicture: String): Flow<List<Author>> {
        TODO("Not yet implemented")
    }

    override fun allAuthors(): Flow<List<Author>> {
        return authorDao.allAuthors().map { it.map(CachedAuthor::asExternalModel) }
    }
}