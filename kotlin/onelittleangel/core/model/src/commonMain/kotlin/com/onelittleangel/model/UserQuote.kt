package com.onelittleangel.model

data class UserQuote internal constructor(
    val idQuote: String,
    val language: String,
    val quote: String,
    val source: String?,
    val reference: String?,
    val remarque: String?,
    val comment: String?,
    val commentName: String?
) : UserResource(followableTopics = emptyList(), isSaved = false) {
    constructor(quote: Quote, userData: UserData) : this(
        idQuote = quote.idQuote,
        language = quote.language,
        quote = quote.quote,
        source = quote.source,
        reference = quote.reference,
        remarque = quote.remarque,
        comment = quote.comment,
        commentName = quote.commentName
    ) {
        followableTopics = quote.topics?.map {
            FollowableTopic(
                topic = it,
                isFollowed = userData.bookmarkedResources.contains(it.id)
            )
        }
        isSaved = userData.bookmarkedResources.contains(quote.idQuote)
    }
}

//public var language: String
//public var quote: String
//public var source: String?
//public var reference: String?
//public var remarque: String?
//public var comment: String?
//public var commentName: String?