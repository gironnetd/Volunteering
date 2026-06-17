package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.faith.FaithDao
import com.onelittleangel.cache.model.CachedMovement
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.FaithRepository
import com.onelittleangel.model.Faith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultFaithRepository(private val faithDao: FaithDao) : FaithRepository {
    override fun faithById(idFaith: String): Flow<Faith> {
        return faithDao.faithById(idFaith).map { it.asExternalModel() }
    }

    override fun faithByName(name: String): Flow<Faith> {
        return faithDao.faithByName(name).map {
            it.asExternalModel()
        }
    }

    override fun faithsByIdParent(idParent: String): Flow<List<Faith>> {
        return faithDao.faithsByIdParent(idParent).map { it.map(CachedMovement::asExternalModel) }
    }

    override fun mainFaiths(): Flow<List<Faith>> {
        return faithDao.mainFaiths().map { it.map(CachedMovement::asExternalModel) }
    }

    override fun allFaiths(): Flow<List<Faith>> {
        return faithDao.allFaiths().map { it.map(CachedMovement::asExternalModel) }
    }
}