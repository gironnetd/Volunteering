package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.url.UrlDao
import com.onelittleangel.cache.model.CachedUrl
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.UrlRepository
import com.onelittleangel.model.Url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultUrlRepository(private val urlDao: UrlDao) : UrlRepository {

    override fun urlById(idUrl: String): Flow<Url> {
        return urlDao.urlById(idUrl).map { it.asExternalModel() }
    }

    override fun urlsByIdAuthor(idAuthor: String): Flow<List<Url>> {
        return urlDao.urlsByIdAuthor(idAuthor).map { it.map(CachedUrl::asExternalModel) }
    }

    override fun urlsByIdBook(idBook: String): Flow<List<Url>> {
        return urlDao.urlsByIdBook(idBook).map { it.map(CachedUrl::asExternalModel) }
    }

    override fun urlsByIdBiography(idBiography: String): Flow<List<Url>> {
        return  urlDao.urlsByIdBiography(idBiography).map { it.map(CachedUrl::asExternalModel) }
    }

    override fun urlsByIdSource(idSource: String): Flow<List<Url>> {
        return urlDao.urlsByIdSource(idSource).map { it.map(CachedUrl::asExternalModel) }
    }

    override fun allUrls(): Flow<List<Url>> {
        return urlDao.allUrls().map { it.map(CachedUrl::asExternalModel) }
    }
}