package com.onelittleangel.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class UserBook internal constructor(
    val idBook: String,
    val name: String,
    val language: String,
    val idRelatedBooks: List<String>? = emptyList(),
    val century: Century?,
    val details: String?,
    val period: String?,
    val idMovement: String?,
    val biography: MutableState<UserBiography?>,
    val mcc1: String?,
    val quotes: SnapshotStateList<UserQuote> = mutableStateListOf(),
    val pictures: SnapshotStateList<UserPicture>? = mutableStateListOf(),
    val urls: MutableList<Url>? = mutableListOf()
) : UserResource(followableTopics = emptyList(), isSaved = false) {
    constructor(book: Book, userData: UserData) : this(
        idBook = book.idBook,
        name = book.name,
        language = book.language,
        idRelatedBooks = book.idRelatedBooks,
        century = book.century,
        details = book.details,
        period = book.period,
        idMovement = book.idMovement,
        biography = mutableStateOf(
            book.biography?.let { UserBiography(biography = it, userData = userData) }
        ),
        mcc1 = book.mcc1,
        quotes = mutableStateListOf<UserQuote>().apply {
            this.addAll(book.quotes.map { UserQuote(quote = it, userData = userData) })
        },
        pictures = mutableStateListOf<UserPicture>().apply {
            book.pictures?.map { UserPicture(picture = it, userData = userData) }
                ?.let { this.addAll(it) }
        },
        urls = book.urls?.toMutableList()
        ) {
        followableTopics = book.topics?.map {
            FollowableTopic(
                topic = it,
                isFollowed = userData.bookmarkedResources.contains(it.id)
            )
        }
        isSaved = userData.bookmarkedResources.contains(book.idBook)
    }
}