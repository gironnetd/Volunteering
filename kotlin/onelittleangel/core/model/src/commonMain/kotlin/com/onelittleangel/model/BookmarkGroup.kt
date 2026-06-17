package com.onelittleangel.model

data class BookmarkGroup(
    val id: String,
    val idParent: String?,
    val location: BookmarkGroupLocation,
    var directoryName: String,
    val shortDescription: String,
    val groups: List<BookmarkGroup> = emptyList(),
    val bookmarks: List<Bookmark> = emptyList(),
)