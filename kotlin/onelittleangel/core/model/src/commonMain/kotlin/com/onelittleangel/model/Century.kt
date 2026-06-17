package com.onelittleangel.model

data class Century(
    val idCentury: String,
    val century: String,
    //presentations: [String : String]?,
    val topics: List<Topic>? = emptyList()
)

//public var idCentury: String
//public var century: String
//public var presentations: [String : String]?
//public var topics: [Topic]?