package com.onelittleangel.domain.century

import com.onelittleangel.data.source.CenturyRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.model.Century
import com.onelittleangel.model.UserResource
import kotlinx.coroutines.flow.Flow

class GetCenturiesUseCase(
    private val centuryRepository: CenturyRepository,
    private val userDataRepository: UserDataRepository
) {

    operator fun invoke(): Flow<List<Century>> {
        return centuryRepository.allCenturies()
    }
}