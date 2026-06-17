package com.onelittleangel.data.source

import com.onelittleangel.model.Picture
import kotlinx.coroutines.flow.Flow

interface PictureRepository {

    fun pictureById(idPicture: String): Flow<Picture>

    fun picturesByIds(ids: List<String>): Flow<List<Picture>>

    fun picturesByIdAuthor (idAuthor: String): Flow<List<Picture>>

    fun picturesByIdBook (idBook: String): Flow<List<Picture>>

    fun picturesByIdBiography(idBiography: String): Flow<List<Picture>>

    fun picturesByIdTheme (idTheme: String): Flow<List<Picture>>

    fun picturesByNameSmall (nameSmall: String): Flow<List<Picture>>

    fun allPictures(): Flow<List<Picture>>
}