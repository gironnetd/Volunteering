package com.onelittleangel.cache.model

import com.onelittleangel.model.Theme
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedTheme : RealmObject {

    @PrimaryKey
    var idTheme: String = ""
    var idParentTheme: String? = null
    var name: String = ""
    var language: String = ""
    var idRelatedThemes: RealmList<String> = realmListOf()
    var presentation: String? = null
    var sourcePresentation: String? = null
    var nbQuotes: Long = 0
    var authors: RealmList<CachedAuthor> = realmListOf()
    var books: RealmList<CachedBook> = realmListOf()
    var themes: RealmList<CachedTheme> = realmListOf()
    var pictures: RealmList<CachedPicture> = realmListOf()
    var quotes: RealmList<CachedQuote> = realmListOf()
    var urls: RealmList<CachedUrl> = realmListOf()
    var topics: RealmList<CachedTopic> = realmListOf()
}

fun CachedTheme.asExternalModel(): Theme {
    return Theme(
        idTheme = idTheme,
        idParentTheme = idParentTheme,
        name = name,
        language = language,
        idRelatedThemes = idRelatedThemes,
        presentation = presentation,
        sourcePresentation = sourcePresentation,
        nbQuotes = nbQuotes,
        authors = null /*authors.map { it.asExternalModel() }*/,
        books = null /*books.map { it.asExternalModel() }*/,
        quotes = quotes.map { it.asExternalModel() },
        themes = themes.map { it.asExternalModel() },
        //faiths = movements.map { it.asExternalModel() },
        pictures = pictures.map { it.asExternalModel() },
        urls = urls.map { it.asExternalModel() },
        topics = topics.map { it.asExternalModel() }
    )
}
