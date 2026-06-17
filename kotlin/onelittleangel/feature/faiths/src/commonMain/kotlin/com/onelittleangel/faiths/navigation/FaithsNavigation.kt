package com.onelittleangel.faiths.navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

const val faithsRoute = "/faiths_route"
const val faithRoute = "/faith_route"
const val faithNameArg = "faithName"

fun Navigator.navigateToFaiths(navOptions: NavOptions? = null) {
    this.navigate(faithsRoute, navOptions)
}

fun Navigator.navigateToFaith(name: String, navOptions: NavOptions? = null) {
    this.navigate("$faithRoute/$name", navOptions)
}

/*fun RouteBuilder.faithsScreen(
    lazyListState: LazyListState,
    onTopicClick: (String) -> Unit,
) {
    scene(faithsRoute) { backStackEntry ->
        FaithsRoute(onBackClick = {}, onFaithClick = {},state = lazyListState)
    }
}*/
