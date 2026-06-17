package com.onelittleangel.domain.bookmark

import com.onelittleangel.data.source.BiographyRepository
import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.data.source.PictureRepository
import com.onelittleangel.data.source.QuoteRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.ResourceKind
import com.onelittleangel.model.UserBiography
import com.onelittleangel.model.UserPicture
import com.onelittleangel.model.UserQuote
import com.onelittleangel.model.UserResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetBookmarksUseCase(
    private val quoteRepository: QuoteRepository,
    private val pictureRepository: PictureRepository,
    private val biographyRepository: BiographyRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val olaPreferencesDataSource: OlaPreferencesDataSource
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<UserResource>> {
        return combine(
            olaPreferencesDataSource.userDataStream
        ) { userData ->
            bookmarkRepository.allBookmarks().map { bookmarks ->
                combine(
                    quoteRepository.quotesByIds(bookmarks.filter { it.kind == ResourceKind.quote }.map { it.idResource }),
                    pictureRepository.picturesByIds(bookmarks.filter { it.kind == ResourceKind.picture }.map { it.idResource }),
                    biographyRepository.biographiesByIds(bookmarks.filter { it.kind == ResourceKind.biography }.map { it.idResource })
                ) { quotes, pictures, biographies ->
                    quotes.map { UserQuote(quote = it, userData = userData.first()) } +
                            pictures.map { UserPicture(picture = it, userData = userData.first()) } +
                            biographies.map { UserBiography(biography = it, userData = userData.first()) }
                }
            }.flattenConcat()
        }.flattenConcat()
    }
}