package com.onelittleangel.model

data class Quote(
    val idQuote: String,
    val topics: List<Topic>? = emptyList(),
    val language: String,
    val quote: String,
    val source: String?,
    val reference: String?,
    val remarque: String?,
    val comment: String?,
    val commentName: String?,
)


