package com.onelittleangel.data.source

import com.onelittleangel.model.Url
import kotlinx.coroutines.flow.Flow

interface UrlRepository {

    fun urlById(idUrl: String): Flow<Url>

    fun urlsByIdAuthor(idAuthor: String): Flow<List<Url>>

    fun urlsByIdBook (idBook: String): Flow<List<Url>>

    fun urlsByIdBiography(idBiography: String): Flow<List<Url>>

    fun urlsByIdSource(idSource: String): Flow<List<Url>>

    fun allUrls(): Flow<List<Url>>
}