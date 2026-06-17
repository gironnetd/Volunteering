package com.onelittleangel.cache.dao.url

import com.onelittleangel.cache.model.CachedTheme
import com.onelittleangel.cache.model.CachedUrl
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUrlDao(private val realm: Realm) : UrlDao {

    override fun urlById(idUrl: String): Flow<CachedUrl> = flow {
        realm.query(CachedUrl::class, query = "idUrl == $0", idUrl)
    }

    override fun urlsByIdAuthor(idAuthor: String): Flow<List<CachedUrl>> = flow {
        realm.query(CachedUrl::class, query = "idAuthor == $0", idAuthor)
    }

    override fun urlsByIdBook(idBook: String): Flow<List<CachedUrl>> = flow {
        realm.query(CachedUrl::class, query = "idBook == $0", idBook)
    }

    override fun urlsByIdBiography(idBiography: String): Flow<List<CachedUrl>> = flow {
        realm.query(CachedUrl::class, query = "idBiography == $0", idBiography)
    }

    override fun urlsByIdSource(idSource: String): Flow<List<CachedUrl>> = flow {
        realm.query(CachedUrl::class, query = "idSource == $0", idSource)
    }

    override fun allUrls(): Flow<List<CachedUrl>> = flow {
        realm.query(CachedUrl::class)
    }
}