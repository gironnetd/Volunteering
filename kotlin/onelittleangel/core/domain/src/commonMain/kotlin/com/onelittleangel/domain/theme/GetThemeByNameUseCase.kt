package com.onelittleangel.domain.theme

import com.onelittleangel.data.source.ThemeRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.UserTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetThemeByNameUseCase(
    private val themeRepository: ThemeRepository,
    private val userDataRepository: UserDataRepository,
    private val olaPreferencesDataSource: OlaPreferencesDataSource
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(name: String): Flow<UserTheme> {
        return combine(
            olaPreferencesDataSource.userDataStream
        ) { userData ->
            themeRepository.themeByName(name = name).map  { theme ->
                UserTheme(theme = theme, userData = userData.first())
            }
        }.flattenConcat()
    }
}