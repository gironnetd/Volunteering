package com.onelittleangel.model

data class Picture(
    val idPicture: String,
    var idData: Long,
    val nameSmall: String,
    val extension: String,
    val comments: Map<String, String>?,
    val width : Long,
    val height : Long,
    val portrait : Boolean,
    val picture: ByteArray?,
    val topics: List<Topic> = emptyList()
)
