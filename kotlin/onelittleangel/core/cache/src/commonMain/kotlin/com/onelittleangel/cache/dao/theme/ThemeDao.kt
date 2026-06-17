package com.onelittleangel.cache.dao.theme

import com.onelittleangel.cache.model.CachedTheme
import kotlinx.coroutines.flow.Flow

interface ThemeDao {

    fun themeById(idTheme: String): Flow<CachedTheme>

    fun themeByName(name: String): Flow<CachedTheme>

    fun themesByIdParent(idParent: String): Flow<List<CachedTheme>>

    fun mainThemes(): Flow<List<CachedTheme>>

    fun allThemes(): Flow<List<CachedTheme>>
}