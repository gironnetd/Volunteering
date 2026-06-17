package com.onelittleangel.domain.faith

import com.onelittleangel.data.source.FaithRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserFaith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetFaithsUseCase(
    private val faithRepository: FaithRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(): Flow<List<UserFaith>> {
        return combine(
            userDataRepository.userData,
            faithRepository.allFaiths()
        ) { userData, faiths ->
            faiths.map { UserFaith(faith = it, userData = userData) }
        }
    }
}