package com.onelittleangel.domain.faith

import com.onelittleangel.data.source.FaithRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.UserFaith
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetFaithByNameUseCase(
    private val faithRepository: FaithRepository,
    private val userDataRepository: UserDataRepository,
    private val olaPreferencesDataSource: OlaPreferencesDataSource
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(name: String): Flow<UserFaith> {
        return combine(
            olaPreferencesDataSource.userDataStream
        ) { userData ->
            faithRepository.faithByName(name = name).map  { faith ->
                UserFaith(faith = faith, userData = userData.first())
            }
        }.flattenConcat()
    }
}