package com.onelittleangel.cache.model

import com.onelittleangel.model.Faith
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CachedMovement : RealmObject {

    @PrimaryKey
    var idMovement: String = ""
    var idParentMovement: String? = null
    var name: String = ""
    var language: String = ""
    var idRelatedMovements: RealmList<String> = realmListOf()
    var mcc1: String? = null
    var mcc2: String? = null
    var presentation: CachedPresentation? = null
    var mcc3: String? = null
    var nbQuotes: Long = 0
    var nbAuthors: Long = 0
    var nbAuthorsQuotes: Long = 0
    var nbBooks: Long = 0
    var nbBooksQuotes: Long = 0
    var selected: Boolean = false
    var nbTotalQuotes: Long = 0
    var nbTotalAuthors: Long = 0
    var nbTotalBooks: Long = 0
    var nbSubcourants: Long = 0
    var nbAuthorsSubcourants: Long = 0
    var nbBooksSubcourants: Long = 0
    var authors: RealmList<CachedAuthor> = realmListOf()
    var books: RealmList<CachedBook> = realmListOf()
    var movements: RealmList<CachedMovement> = realmListOf()
    var pictures: RealmList<CachedPicture> = realmListOf()
    var urls: RealmList<CachedUrl> = realmListOf()
    var topics: RealmList<CachedTopic> = realmListOf()
}

fun CachedMovement.asExternalModel(): Faith {
    return Faith(
        idMovement = idMovement,
        idParentMovement = idParentMovement,
        name = name,
        language = language,
        idRelatedMovements = idRelatedMovements,
        mcc1 = mcc1,
        mcc2 = mcc2,
        biography  = presentation?.asExternalModel(),
        mcc3 = mcc3,
        nbQuotes =  nbQuotes,
        nbAuthors = nbAuthors,
        nbAuthorsQuotes = nbAuthorsQuotes,
        nbBooks = nbBooks,
        nbBooksQuotes = nbBooksQuotes,
        selected = selected,
        nbTotalQuotes = nbTotalQuotes,
        nbTotalAuthors = nbTotalAuthors,
        nbTotalBooks = nbTotalBooks,
        nbSubcourants = nbSubcourants,
        nbAuthorsSubcourants = nbAuthorsSubcourants,
        nbBooksSubcourants = nbBooksSubcourants,
        authors = authors.map { it.asExternalModel() },
        books = books.map { it.asExternalModel() },
        faiths = movements.map { it.asExternalModel() },
        pictures = pictures.map { it.asExternalModel() },
        urls = urls.map { it.asExternalModel() },
        topics = topics.map { it.asExternalModel() }
    )
}
