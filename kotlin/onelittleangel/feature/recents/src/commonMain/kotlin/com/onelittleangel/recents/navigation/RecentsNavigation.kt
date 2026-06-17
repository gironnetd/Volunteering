package com.onelittleangel.recents.navigation

import com.onelittleangel.recents.RecentsRoute
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

const val recentsRoute = "/recents_route"

fun Navigator.navigateToRecents(navOptions: NavOptions? = null) {
    this.navigate(recentsRoute, navOptions)
}

fun RouteBuilder.recentsScreen(
    onTopicClick: (String) -> Unit,
    //onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    scene(recentsRoute) { backStackEntry ->
        RecentsRoute()
    }
}

