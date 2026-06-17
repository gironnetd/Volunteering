package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.century.CenturyDao
import com.onelittleangel.cache.model.CachedCentury
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.CenturyRepository
import com.onelittleangel.model.Century
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultCenturyRepository(private val centuryDao: CenturyDao) : CenturyRepository {

    override fun centuryById(idCentury: String): Flow<Century> {
        return centuryDao.centuryById(idCentury).map { it.asExternalModel() }
    }

    override fun centuryByIdAuthor(idAuthor: String): Flow<Century> {
        return centuryDao.centuryByIdAuthor(idAuthor).map { it.asExternalModel() }
    }

    override fun centuryByIdBook(idBook: String): Flow<Century> {
        return centuryDao.centuryByIdBook(idBook).map { it.asExternalModel() }
    }

    override fun centuryByName(name: String): Flow<Century> {
        return centuryDao.centuryByName(name).map { it.asExternalModel() }
    }

    override fun allCenturies(): Flow<List<Century>> {
        return centuryDao.allCenturies().map { it.map(CachedCentury::asExternalModel) }
    }
}