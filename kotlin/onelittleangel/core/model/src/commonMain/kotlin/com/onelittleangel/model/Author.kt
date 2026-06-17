package com.onelittleangel.model

data class Author(
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
    val biography: Biography?,
    val mainPicture: Long?,
    val mcc1: String?,
    val quotes: List<Quote> = emptyList(),
    val pictures: List<Picture>? = emptyList(),
    val urls: List<Url>? = emptyList(),
    val topics: List<Topic>? = emptyList()
)
