package com.onelittleangel.model

data class Theme(
    val idTheme: String,
    val idParentTheme: String?,
    val name: String,
    val language: String,
    val idRelatedThemes: List<String>? = emptyList(),
    val presentation: String?,
    val sourcePresentation: String?,
    val nbQuotes: Long = 0,
    val authors: List<Author>? = emptyList(),
    val books: List<Book>? = emptyList(),
    val themes: List<Theme>? = emptyList(),
    val pictures: List<Picture>? = emptyList(),
    val urls: List<Url>? = emptyList(),
    val quotes: List<Quote> = emptyList(),
    val topics: List<Topic>? = emptyList()
)