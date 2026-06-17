package com.onelittleangel.cache.model

import com.onelittleangel.model.Book
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedBook : RealmObject {

    @PrimaryKey
    var idBook: String = ""
    var name: String = ""
    var language: String = ""
    var idRelatedBooks: RealmList<String> = realmListOf()
    var century: CachedCentury? = null
    var details: String? = null
    var period: String? = null
    var idMovement: String? = null
    var presentation: CachedPresentation? = null
    var mcc1: String? = null
    var quotes: RealmList<CachedQuote> = realmListOf()
    var pictures: RealmList<CachedPicture> = realmListOf()
    var urls: RealmList<CachedUrl> = realmListOf()
    var topics: RealmList<CachedTopic> = realmListOf()
}

fun CachedBook.asExternalModel() = Book(
    idBook = idBook,
    name = name,
    language = language,
    idRelatedBooks = idRelatedBooks,
    century = century?.asExternalModel(),
    details = details,
    period = period,
    idMovement = idMovement,
    biography = presentation?.asExternalModel(),
    mcc1 = mcc1,
    quotes = quotes.map { it.asExternalModel() },
    pictures = pictures.map { it.asExternalModel() },
    urls = urls.map { it.asExternalModel() },
    topics = topics.map { it.asExternalModel() }
)
