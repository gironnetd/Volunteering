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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetBookmarkGroupByIdUseCase(
    private val quoteRepository: QuoteRepository,
    private val pictureRepository: PictureRepository,
    private val biographyRepository: BiographyRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val olaPreferencesDataSource: OlaPreferencesDataSource
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(id: String): Flow<List<UserResource>> {
        return combine(
            olaPreferencesDataSource.userDataStream
        ) { userData ->
            bookmarkRepository.groupById(id).map { group ->
                group.bookmarks.sortedByDescending { it.dateCreation }.mapNotNull { bookmark ->
                    when(bookmark.kind) {
                        ResourceKind.quote -> quoteRepository.quoteById(idQuote = bookmark.idResource).map {
                            UserQuote(quote = it, userData = userData.first())
                        }
                        ResourceKind.picture -> pictureRepository.pictureById(idPicture = bookmark.idResource)
                            .map { UserPicture(picture = it, userData = userData.first()) }
                        ResourceKind.biography -> biographyRepository.biographyById(idBiography = bookmark.idResource)
                            .map { UserBiography(biography = it, userData = userData.first()) }
                        else -> null
                    }?.first()
                }.map { it.apply { this.isSaved = true } }
            }
        }.flattenConcat()
    }
}