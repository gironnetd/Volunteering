package com.onelittleangel.data.repository

import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.DarkThemeConfig
import com.onelittleangel.model.ThemeBrand
import com.onelittleangel.model.UserData
import kotlinx.coroutines.flow.Flow

class DefaultUserDataRepository(
    private val olaPreferencesDataSource: OlaPreferencesDataSource
) : UserDataRepository {

    override val userData: Flow<UserData> = olaPreferencesDataSource.userDataStream

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) =
        olaPreferencesDataSource.setThemeBrand(themeBrand)

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) =
        olaPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)

    override suspend fun updateResourceBookmarked(idResource: String, bookmarked: Boolean) =
        olaPreferencesDataSource.setResourceBookmarked(idResource, bookmarked)
}