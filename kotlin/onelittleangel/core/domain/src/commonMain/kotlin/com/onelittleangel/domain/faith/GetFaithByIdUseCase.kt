package com.onelittleangel.domain.faith

import com.onelittleangel.data.source.FaithRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.UserFaith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetFaithByIdUseCase(
    private val faithRepository: FaithRepository,
    private val userDataRepository: UserDataRepository
) {
    operator fun invoke(idFaith: String): Flow<UserFaith> {
        return combine(
            userDataRepository.userData,
            faithRepository.faithById(idFaith = idFaith)
        ) { userData, faith ->
            UserFaith(faith = faith, userData = userData)
        }
    }
}