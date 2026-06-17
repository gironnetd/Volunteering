package com.onelittleangel.themes.navigation

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator

const val themesRoute = "/themes_route"
const val themeRoute = "/theme_route"
const val themeNameArg = "themeName"

fun Navigator.navigateToThemes(navOptions: NavOptions? = null) {
    this.navigate(themesRoute, navOptions)
}

fun Navigator.navigateToTheme(name: String, navOptions: NavOptions? = null) {
    this.navigate("$themeRoute/$name", navOptions)
}

/*fun RouteBuilder.themesScreen(
    lazyListState: LazyListState,
    onTopicClick: (String) -> Unit,
) {
    scene(themesRoute) { backStackEntry ->
        ThemesRoute(onBackClick = {}, onThemeClick = {}, lazyListState = lazyListState)
    }
}*/
