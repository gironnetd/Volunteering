package com.onelittleangel.model

data class Book(
    val idBook: String,
    val name: String,
    val language: String,
    val idRelatedBooks: List<String>? = emptyList(),
    val century: Century?,
    val details: String?,
    val period: String?,
    val idMovement: String?,
    val biography: Biography?,
    val mcc1: String?,
    val quotes: List<Quote> = emptyList(),
    val pictures: List<Picture>? = emptyList(),
    val urls: List<Url>? = emptyList(),
    val topics: List<Topic>? = emptyList()
)