package com.onelittleangel.data.source

import com.onelittleangel.model.Century
import kotlinx.coroutines.flow.Flow

interface CenturyRepository {

    fun centuryById(idCentury: String): Flow<Century>

    fun centuryByIdAuthor (idAuthor: String): Flow<Century>

    fun centuryByIdBook (idBook: String): Flow<Century>

    fun centuryByName (name: String): Flow<Century>

    fun allCenturies(): Flow<List<Century>>
}