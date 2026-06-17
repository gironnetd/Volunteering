package com.onelittleangel.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class UserAuthor internal constructor(
    val idAuthor: String,
    val name: String,
    val language: String,
    val idRelatedAuthors: List<String>? = emptyList(),
    val century: Century?,
    val surname: String?,
    val details: String?,
    val period: String?,
    val idMovement: String?,
    val bibliography: String?,
    val biography: MutableState<UserBiography?>,
    val mainPicture: Long?,
    val mcc1: String?,
    val quotes: SnapshotStateList<UserQuote> = mutableStateListOf(),
    val pictures: SnapshotStateList<UserPicture>? = mutableStateListOf(),
    val urls: MutableList<Url>? = mutableListOf()
) : UserResource(followableTopics = emptyList(), isSaved = false) {
    constructor(author: Author, userData: UserData) : this(
        idAuthor = author.idAuthor,
        name = author.name,
        language = author.language,
        idRelatedAuthors = author.idRelatedAuthors,
        century = author.century,
        surname = author.surname,
        details = author.details,
        period = author.period,
        idMovement = author.idMovement,
        bibliography = author.bibliography,
        biography = mutableStateOf(
            author.biography?.let { UserBiography(biography = it, userData = userData) }
        ),
        mainPicture = author.mainPicture,
        mcc1 = author.mcc1,
        quotes = mutableStateListOf<UserQuote>().apply {
            this.addAll(author.quotes.map { UserQuote(quote = it, userData = userData) })
        },
        pictures = mutableStateListOf<UserPicture>().apply {
            author.pictures?.map { UserPicture(picture = it, userData = userData) }
                ?.let { this.addAll(it) }
        },
        urls = author.urls?.toMutableList()
    ) {
        followableTopics = author.topics?.map {
            FollowableTopic(
                topic = it,
                isFollowed = userData.bookmarkedResources.contains(it.id)
            )
        }
        isSaved = userData.bookmarkedResources.contains(author.idAuthor)
    }
}
