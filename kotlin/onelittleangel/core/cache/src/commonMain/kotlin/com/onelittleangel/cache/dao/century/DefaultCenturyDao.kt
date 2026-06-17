package com.onelittleangel.cache.dao.century

import com.onelittleangel.cache.model.CachedBook
import com.onelittleangel.cache.model.CachedBookmark
import com.onelittleangel.cache.model.CachedCentury
import io.realm.kotlin.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultCenturyDao(private val realm: Realm) : CenturyDao {

    override fun centuryById(idCentury: String): Flow<CachedCentury> = flow {
        realm.query(CachedCentury::class, query = "idCentury == $0", idCentury)
    }

    override fun centuryByIdAuthor(idAuthor: String): Flow<CachedCentury> = flow {
        realm.query(CachedCentury::class, query = "idAuthor == $0", idAuthor)
    }

    override fun centuryByIdBook(idBook: String): Flow<CachedCentury> = flow {
        realm.query(CachedCentury::class, query = "idBook == $0", idBook)
    }

    override fun centuryByName(name: String): Flow<CachedCentury> = flow {
        realm.query(CachedCentury::class, query = "name == $0", name)
    }

    override fun allCenturies(): Flow<List<CachedCentury>> = flow {
        realm.query(CachedCentury::class)
    }
}