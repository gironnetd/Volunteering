package com.onelittleangel.authors.navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

const val authorsRoute = "/authors_route"
const val authorRoute = "/author_route"
const val authorNameArg = "authorName"

fun Navigator.navigateToAuthors(navOptions: NavOptions? = null) {
    this.navigate(authorsRoute, navOptions)
}

fun Navigator.navigateToAuthor(name: String, navOptions: NavOptions? = null) {
    this.navigate("$authorRoute/$name", navOptions)
}

