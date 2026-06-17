package com.onelittleangel.books.navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

const val booksRoute = "/books_route"
const val bookRoute = "/book_route"
const val bookNameArg = "bookName"

fun Navigator.navigateToBooks(navOptions: NavOptions? = null) {
    this.navigate(booksRoute, navOptions)
}

fun Navigator.navigateToBook(name: String, navOptions: NavOptions? = null) {
    this.navigate("$bookRoute/$name", navOptions)
}
