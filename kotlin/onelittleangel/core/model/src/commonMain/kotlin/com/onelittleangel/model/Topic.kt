package com.onelittleangel.model

data class Topic(
    val id: String,
    val language: String,
    val name: String,
    val shortDescription: String?,
    val longDescription: String?,
    val idResource: String,
    val kind: ResourceKind,
)

//public let id: String
//public let language: String
//public let name: String
//public let shortDescription: String?
//public let longDescription: String?
//public let idResource: String
//public let kind: ResourceKind