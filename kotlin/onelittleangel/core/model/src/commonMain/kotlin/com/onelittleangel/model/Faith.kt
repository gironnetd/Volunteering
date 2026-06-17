package com.onelittleangel.model

data class Faith(
    val idMovement: String,
    val idParentMovement: String?,
    val name: String,
    val language: String,
    val idRelatedMovements: List<String>? = emptyList(),
    val mcc1: String?,
    val mcc2: String?,
    val biography: Biography?,
    val mcc3: String?,
    val nbQuotes: Long,
    val nbAuthors: Long,
    val nbAuthorsQuotes: Long,
    val nbBooks: Long,
    val nbBooksQuotes: Long,
    val selected: Boolean = false,
    val nbTotalQuotes: Long,
    val nbTotalAuthors: Long,
    val nbTotalBooks: Long,
    val nbSubcourants: Long,
    val nbAuthorsSubcourants: Long,
    val nbBooksSubcourants: Long,
    val authors: List<Author>? = emptyList(),
    val books: List<Book>? = emptyList(),
    val faiths: List<Faith> = emptyList(),
    val pictures: List<Picture>? = emptyList(),
    val urls: List<Url>? = emptyList(),
    val topics: List<Topic>? = emptyList()
)

//public var idMovement: String
//public var idParentMovement: String?
//public var name: String
//public var language: String
//public var idRelatedMovements: [String]?
//public var mcc1: String?
//public var mcc2: String?
//public var presentation: Presentation?
//public var mcc3: String?
//public var nbQuotes: Int
//public var nbAuthors: Int
//public var nbAuthorsQuotes: Int
//public var nbBooks: Int
//public var nbBooksQuotes: Int
//public var selected: Bool = false
//public var nbTotalQuotes: Int
//public var nbTotalAuthors: Int
//public var nbTotalBooks: Int
//public var nbSubcourants: Int
//public var nbAuthorsSubcourants: Int
//public var nbBooksSubcourants: Int
//public var authors: [Author]?
//public var books: [Book]?
//public var movements: [Movement]?
//public var pictures: [Picture]?
//public var urls: [Url]?
//public var topics: [Topic]?