package com.onelittleangel.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class UserTheme internal constructor(
    val idTheme: String,
    val idParentTheme: String?,
    val name: String,
    val language: String,
    val idRelatedThemes: List<String>? = emptyList(),
    val presentation: String?,
    val sourcePresentation: String?,
    val nbQuotes: Long = 0,
    val authors: List<UserAuthor>? = emptyList(),
    val books: List<UserBook>? = emptyList(),
    val themes: List<UserTheme>? = emptyList(),
    val pictures: SnapshotStateList<UserPicture>? = mutableStateListOf(),
    val quotes: SnapshotStateList<UserQuote> = mutableStateListOf(),
    val urls: MutableList<Url>? = mutableListOf()
) : UserResource(followableTopics = emptyList(), isSaved = false) {
    constructor(theme: Theme, userData: UserData) : this(
        idTheme = theme.idTheme,
        idParentTheme = theme.idParentTheme,
        name = theme.name,
        language = theme.language,
        idRelatedThemes = theme.idRelatedThemes,
        presentation = theme.presentation,
        sourcePresentation = theme.sourcePresentation,
        nbQuotes = theme.nbQuotes,
        authors = theme.authors?.map { UserAuthor(author = it, userData = userData) },
        books = theme.books?.map { UserBook(book = it, userData = userData) },
        themes = theme.themes?.map { UserTheme(theme = it, userData = userData) },
        pictures = mutableStateListOf<UserPicture>().apply {
            theme.pictures?.map { UserPicture(picture = it, userData = userData) }
                ?.let { this.addAll(it) }
        },
        quotes = mutableStateListOf<UserQuote>().apply {
            this.addAll(theme.quotes.map { UserQuote(quote = it, userData = userData) })
        },
        urls = theme.urls?.toMutableList()
        ) {
        followableTopics = theme.topics?.map {
            FollowableTopic(
                topic = it,
                isFollowed = userData.bookmarkedResources.contains(it.id)
            )
        }
        isSaved = userData.bookmarkedResources.contains(theme.idTheme)
    }
}