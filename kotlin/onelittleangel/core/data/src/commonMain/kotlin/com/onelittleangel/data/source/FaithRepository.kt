package com.onelittleangel.data.source

import com.onelittleangel.model.Faith
import kotlinx.coroutines.flow.Flow

interface FaithRepository {

    fun faithById(idFaith: String): Flow<Faith>

    fun faithByName(name: String): Flow<Faith>

    fun faithsByIdParent(idParent: String): Flow<List<Faith>>

    fun mainFaiths(): Flow<List<Faith>>

    fun allFaiths(): Flow<List<Faith>>
}