package com.onelittleangel.data.repository

import com.onelittleangel.cache.dao.theme.ThemeDao
import com.onelittleangel.cache.model.CachedTheme
import com.onelittleangel.cache.model.asExternalModel
import com.onelittleangel.data.source.ThemeRepository
import com.onelittleangel.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultThemeRepository(private val themeDao: ThemeDao) : ThemeRepository {

    override fun themeById(idTheme: String): Flow<Theme> {
        return themeDao.themeById(idTheme).map { it.asExternalModel() }
    }

    override fun themeByName(name: String): Flow<Theme> {
        return themeDao.themeByName(name).map { it.asExternalModel() }
    }

    override fun themesByIdParent(idParent: String): Flow<List<Theme>> {
        return themeDao.themesByIdParent(idParent).map { it.map(CachedTheme::asExternalModel) }
    }

    override fun mainThemes(): Flow<List<Theme>> {
        return themeDao.mainThemes().map { it.map(CachedTheme::asExternalModel) }
    }

    override fun allThemes(): Flow<List<Theme>> {
        return themeDao.allThemes().map { it.map(CachedTheme::asExternalModel) }
    }
}