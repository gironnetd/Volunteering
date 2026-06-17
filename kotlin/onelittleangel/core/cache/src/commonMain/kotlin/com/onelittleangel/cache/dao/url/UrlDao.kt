package com.onelittleangel.cache.dao.url

import com.onelittleangel.cache.model.CachedUrl
import kotlinx.coroutines.flow.Flow

interface UrlDao {

    fun urlById(idUrl: String): Flow<CachedUrl>

    fun urlsByIdAuthor(idAuthor: String): Flow<List<CachedUrl>>

    fun urlsByIdBook (idBook: String): Flow<List<CachedUrl>>

    fun urlsByIdBiography(idBiography: String): Flow<List<CachedUrl>>

    fun urlsByIdSource(idSource: String): Flow<List<CachedUrl>>

    fun allUrls(): Flow<List<CachedUrl>>
}