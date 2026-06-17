package com.onelittleangel.data.source

import com.onelittleangel.model.DarkThemeConfig
import com.onelittleangel.model.ThemeBrand
import com.onelittleangel.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    /// Set the current theme brand
    ///
    /// - Returns: Void or throws an Error
    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    /// Set the current  dark theme config
    ///
    /// - Returns: Void or throws an Error
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun updateResourceBookmarked(idResource: String, bookmarked: Boolean)
}