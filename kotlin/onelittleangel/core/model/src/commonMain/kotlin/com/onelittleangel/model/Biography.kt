package com.onelittleangel.model

data class Biography(
    val idPresentation: String,
    val language: String,
    val presentation: String?,
    val presentationTitle1: String?,
    val presentation1: String?,
    val presentationTitle2: String?,
    val presentation2: String?,
    val presentationTitle3: String?,
    val presentation3: String?,
    val presentationTitle4: String?,
    val presentation4: String?,
    val sourcePresentation: String?,
    val topics: List<Topic> = emptyList(),
)

