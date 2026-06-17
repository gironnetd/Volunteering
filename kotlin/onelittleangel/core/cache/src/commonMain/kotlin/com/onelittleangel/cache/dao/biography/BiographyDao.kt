package com.onelittleangel.cache.dao.biography

import com.onelittleangel.cache.model.CachedBook
import com.onelittleangel.cache.model.CachedMovement
import com.onelittleangel.cache.model.CachedPresentation
import kotlinx.coroutines.flow.Flow

interface BiographyDao {

    fun biographyById(idBiography: String): Flow<CachedPresentation>

    fun biographiesByIds(ids: List<String>): Flow<List<CachedPresentation>>

    fun biographyByIdAuthor(idAuthor: String): Flow<CachedPresentation>

    fun biographyByIdBook(idBook: String): Flow<CachedPresentation>

    fun biographyByIdFaith(idFaith: String): Flow<CachedPresentation>

    fun allBiographies(): Flow<List<CachedPresentation>>
}