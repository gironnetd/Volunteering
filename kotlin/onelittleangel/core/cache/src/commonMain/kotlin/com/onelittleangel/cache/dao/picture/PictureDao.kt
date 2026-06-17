package com.onelittleangel.cache.dao.picture

import com.onelittleangel.cache.model.CachedPicture
import kotlinx.coroutines.flow.Flow

interface PictureDao {

    fun pictureById(idPicture: String): Flow<CachedPicture>

    fun picturesByIds(ids: List<String>): Flow<List<CachedPicture>>

    fun picturesByIdAuthor (idAuthor: String): Flow<List<CachedPicture>>

    fun picturesByIdBook (idBook: String): Flow<List<CachedPicture>>

    fun picturesByIdBiography(idBiography: String): Flow<List<CachedPicture>>

    fun picturesByIdTheme (idTheme: String): Flow<List<CachedPicture>>

    fun picturesByNameSmall (nameSmall: String): Flow<List<CachedPicture>>

    fun allPictures(): Flow<List<CachedPicture>>
}