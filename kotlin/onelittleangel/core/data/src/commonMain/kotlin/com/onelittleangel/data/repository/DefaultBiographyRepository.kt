package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.biography.BiographyDao
import com.onelittleangel.cache.model.CachedPicture
import com.onelittleangel.cache.model.CachedPresentation
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.BiographyRepository
import com.onelittleangel.model.Biography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBiographyRepository(private val biographyDao: BiographyDao) : BiographyRepository {

    override fun biographyById(idBiography: String): Flow<Biography> {
        return biographyDao.biographyById(idBiography).map { it.asExternalModel() }
    }

    override fun biographiesByIds(ids: List<String>): Flow<List<Biography>> {
        return biographyDao.biographiesByIds(ids).map { it.map(CachedPresentation::asExternalModel) }
    }

    override fun biographyByIdAuthor(idAuthor: String): Flow<Biography> {
        return biographyDao.biographyByIdAuthor(idAuthor).map { it.asExternalModel() }
    }

    override fun biographyByIdBook(idBook: String): Flow<Biography> {
        return biographyDao.biographyByIdBook(idBook).map { it.asExternalModel() }
    }

    override fun biographyByIdFaith(idFaith: String): Flow<Biography> {
       return biographyDao.biographyByIdFaith(idFaith).map { it.asExternalModel() }
    }

    override fun allBiographies(): Flow<List<Biography>> {
        return biographyDao.allBiographies().map { it.map(CachedPresentation::asExternalModel) }
    }
}