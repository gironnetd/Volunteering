package com.onelittleangel.model

data class User(
    val id: String,
    val providerID: String,
    val uidUser: String?,
    val email: String?,
    val displayName: String?,
    //val photo: Data?,
    val bookmarks: List<BookmarkGroup> = emptyList(),
)