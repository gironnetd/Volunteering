package com.onelittleangel.domain.foryou

import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.UserBiography
import com.onelittleangel.model.UserPicture
import com.onelittleangel.model.UserQuote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetForYouUseCase(
    private val getForYouQuotesUseCase: GetForYouQuotesUseCase,
    private val getForYouPicturesUseCase: GetForYouPicturesUseCase,
    private val getForYouBiographiesUseCase: GetForYouBiographiesUseCase,
    private val olaPreferences: OlaPreferencesDataSource
) {
    operator fun invoke(): Flow<Triple<List<UserQuote>, List<UserPicture>, List<UserBiography>>> {
        return combine(
            getForYouQuotesUseCase(),
            getForYouPicturesUseCase(),
            getForYouBiographiesUseCase()
        ) { userQuotes, userPictures, userBiographies ->
            Triple(
                first = userQuotes,
                second = userPictures,
                third = userBiographies
            )
        }
    }
}