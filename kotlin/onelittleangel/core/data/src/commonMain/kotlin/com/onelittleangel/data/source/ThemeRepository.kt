package com.onelittleangel.data.source

import com.onelittleangel.model.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    fun themeById(idTheme: String): Flow<Theme>

    fun themeByName(name: String): Flow<Theme>

    fun themesByIdParent(idParent: String): Flow<List<Theme>>

    fun mainThemes(): Flow<List<Theme>>

    fun allThemes(): Flow<List<Theme>>
}