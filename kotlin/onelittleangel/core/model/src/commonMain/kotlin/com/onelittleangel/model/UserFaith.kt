package com.onelittleangel.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class UserFaith internal constructor(
    val idMovement: String,
    val idParentMovement: String?,
    val name: String,
    val language: String,
    val idRelatedMovements: List<String>? = emptyList(),
    val mcc1: String?,
    val mcc2: String?,
    val biography: MutableState<UserBiography?>,
    val mcc3: String?,
    val nbQuotes: Long,
    val nbAuthors: Long,
    val nbAuthorsQuotes: Long,
    val nbBooks: Long,
    val nbBooksQuotes: Long,
    val selected: Boolean = false,
    val nbTotalQuotes: Long,
    val nbTotalAuthors: Long,
    val nbTotalBooks: Long,
    val nbSubcourants: Long,
    val nbAuthorsSubcourants: Long,
    val nbBooksSubcourants: Long,
    val authors: MutableList<UserAuthor>? = mutableListOf(),
    val books: MutableList<UserBook>? = mutableListOf(),
    val faiths: MutableList<UserFaith> = mutableListOf(),
    val pictures: SnapshotStateList<UserPicture>? = mutableStateListOf(),
    val urls: MutableList<Url>? = mutableListOf()
) : UserResource(followableTopics = emptyList(), isSaved = false) {
    constructor(faith: Faith, userData: UserData) : this(
        idMovement = faith.idMovement,
        idParentMovement = faith.idParentMovement,
        name = faith.name,
        language = faith.language,
        idRelatedMovements = faith.idRelatedMovements,
        mcc1 = faith.mcc1,
        mcc2 = faith.mcc2,
        biography = mutableStateOf(
            faith.biography?.let { UserBiography(biography = it, userData = userData) }
        ),
        mcc3 = faith.mcc3,
        nbQuotes = faith.nbQuotes,
        nbAuthors = faith.nbAuthors,
        nbAuthorsQuotes = faith.nbAuthorsQuotes,
        nbBooks = faith.nbBooks,
        nbBooksQuotes = faith.nbBooksQuotes,
        selected = faith.selected,
        nbTotalQuotes = faith.nbTotalQuotes,
        nbTotalAuthors = faith.nbTotalAuthors,
        nbTotalBooks = faith.nbTotalBooks,
        nbSubcourants = faith.nbSubcourants,
        nbAuthorsSubcourants = faith.nbAuthorsSubcourants,
        nbBooksSubcourants = faith.nbBooksSubcourants,
        authors = faith.authors?.map { UserAuthor(author = it, userData = userData) }?.toMutableList(),
        books = faith.books?.map { UserBook(book = it, userData = userData) }?.toMutableList(),
        faiths = faith.faiths.map { UserFaith(faith = it, userData = userData) }.toMutableList(),
        pictures = mutableStateListOf<UserPicture>().apply {
            faith.pictures?.map { UserPicture(picture = it, userData = userData) }
                ?.let { this.addAll(it) }
        },
        urls = faith.urls?.toMutableList()
        ) {
        followableTopics = faith.topics?.map {
            FollowableTopic(
                topic = it,
                isFollowed = userData.bookmarkedResources.contains(it.id)
            )
        }
        isSaved  = userData.bookmarkedResources.contains(faith.idMovement)
    }
}