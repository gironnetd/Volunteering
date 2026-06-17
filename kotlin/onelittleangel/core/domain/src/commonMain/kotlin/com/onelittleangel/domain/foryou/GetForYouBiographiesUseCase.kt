package com.onelittleangel.domain.foryou

import com.onelittleangel.data.source.BiographyRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.UserBiography
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetForYouBiographiesUseCase(
    private val biographyRepository: BiographyRepository,
    private val userDataRepository: UserDataRepository,
    private val olaPreferences: OlaPreferencesDataSource
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<UserBiography>> {
        return olaPreferences.hasDateChanged.map { hasDateChanged ->
            combine(
                olaPreferences.userDataStream,
                (if(hasDateChanged) {
                    biographyRepository.allBiographies().map {
                        it//.filter { it.language.subSequence(0, 2) == Locale.current.language }
                            //.shuffled().subList(0, 3)
                    }
                } else {
                    olaPreferences.dailyBiographyIds.map { ids ->
                        biographyRepository.biographiesByIds(ids.toList())
                    }.flattenConcat()
                })
            ) { userData, biographies ->
                if(hasDateChanged) {
                    olaPreferences.setDailyBiographyIds(biographies.map { it.idPresentation }.toSet())
                }

                biographies.map { UserBiography(biography = it, userData = userData) }
            }
        }.flattenConcat()
    }
}