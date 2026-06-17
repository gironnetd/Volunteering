package com.onelittleangel.model

import kotlinx.datetime.Instant

data class Bookmark(
    var id: String,
    var idBookmarkGroup: String,
    val note: String,
    val idResource: String,
    val kind: ResourceKind,
    val dateCreation: Instant
)