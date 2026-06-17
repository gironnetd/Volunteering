package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.picture.PictureDao
import com.onelittleangel.cache.model.CachedPicture
import com.onelittleangel.cache.model.CachedQuote
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.PictureRepository
import com.onelittleangel.model.Picture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultPictureRepository(private val pictureDao: PictureDao) : PictureRepository {

    override fun pictureById(idPicture: String): Flow<Picture> {
        return pictureDao.pictureById(idPicture).map { it.asExternalModel() }
    }

    override fun picturesByIds(ids: List<String>): Flow<List<Picture>> {
        return pictureDao.picturesByIds(ids).map { it.map(CachedPicture::asExternalModel) }
    }

    override fun picturesByIdAuthor(idAuthor: String): Flow<List<Picture>> {
        return pictureDao.picturesByIdAuthor(idAuthor).map { it.map(CachedPicture::asExternalModel) }
    }

    override fun picturesByIdBook(idBook: String): Flow<List<Picture>> {
        return pictureDao.picturesByIdBook(idBook).map { it.map(CachedPicture::asExternalModel) }
    }

    override fun picturesByIdBiography(idBiography: String): Flow<List<Picture>> {
        return pictureDao.picturesByIdBiography(idBiography).map { it.map(CachedPicture::asExternalModel) }
    }

    override fun picturesByIdTheme(idTheme: String): Flow<List<Picture>> {
        return pictureDao.picturesByIdTheme(idTheme).map { it.map(CachedPicture::asExternalModel) }
    }

    override fun picturesByNameSmall(nameSmall: String): Flow<List<Picture>> {
        return pictureDao.picturesByNameSmall(nameSmall).map { it.map(CachedPicture::asExternalModel) }
    }

    override fun allPictures(): Flow<List<Picture>> {
        return pictureDao.allPictures().map { it.map(CachedPicture::asExternalModel) }
    }
}