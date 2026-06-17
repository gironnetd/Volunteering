package com.onelittleangel.cache.dao.century

import com.onelittleangel.cache.model.CachedCentury
import kotlinx.coroutines.flow.Flow

interface CenturyDao {

    fun centuryById(idCentury: String): Flow<CachedCentury>

    fun centuryByIdAuthor (idAuthor: String): Flow<CachedCentury>

    fun centuryByIdBook (idBook: String): Flow<CachedCentury>

    fun centuryByName (name: String): Flow<CachedCentury>

    fun allCenturies(): Flow<List<CachedCentury>>
}