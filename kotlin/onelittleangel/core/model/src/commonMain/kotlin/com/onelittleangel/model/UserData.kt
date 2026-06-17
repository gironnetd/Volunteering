package com.onelittleangel.model

data class UserData(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val bookmarkedResources: Set<String>,
    val uidUser: String?
)
