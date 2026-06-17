package com.onelittleangel.domain.foryou

import com.onelittleangel.data.source.PictureRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.UserPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetForYouPicturesUseCase(
    private val pictureRepository: PictureRepository,
    private val userDataRepository: UserDataRepository,
    private val olaPreferences: OlaPreferencesDataSource
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<UserPicture>> {
        return olaPreferences.hasDateChanged.map { hasDateChanged ->
            combine(
                olaPreferences.userDataStream,
                (if(hasDateChanged) {
                    pictureRepository.allPictures().map {
                        it//.filter { it.topics.first().language.subSequence(0, 2) == Locale.current.language }
                            //.shuffled().subList(0, 3)
                    }
                } else {
                    olaPreferences.dailyPictureIds.map { ids ->
                        pictureRepository.picturesByIds(ids.toList())
                    }.flattenConcat()
                })
            ) { userData, pictures ->
                if(hasDateChanged) {
                    olaPreferences.setDailyPictureIds(pictures.map { it.idPicture }.toSet())
                }

                pictures.map {
                    UserPicture(picture = it, userData = userData)}
            }
        }.flattenConcat()
    }
}