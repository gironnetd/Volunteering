package com.onelittleangel.data.source

import com.onelittleangel.model.Biography
import kotlinx.coroutines.flow.Flow

interface BiographyRepository {

    fun biographyById(idBiography: String): Flow<Biography>

    fun biographiesByIds(ids: List<String>): Flow<List<Biography>>

    fun biographyByIdAuthor(idAuthor: String): Flow<Biography>

    fun biographyByIdBook(idBook: String): Flow<Biography>

    fun biographyByIdFaith(idFaith: String): Flow<Biography>

    fun allBiographies(): Flow<List<Biography>>
}