package com.onelittleangel.shared.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Upcoming
import androidx.compose.ui.graphics.vector.ImageVector
import com.onelittleangel.authors.navigation.authorNameArg
import com.onelittleangel.authors.navigation.authorRoute
import com.onelittleangel.authors.navigation.authorsRoute
import com.onelittleangel.bookmarks.navigation.bookmarkGroupIdArg
import com.onelittleangel.bookmarks.navigation.bookmarkGroupsRoute
import com.onelittleangel.bookmarks.navigation.bookmarkRoute
import com.onelittleangel.bookmarks.navigation.bookmarksRoute
import com.onelittleangel.books.navigation.bookNameArg
import com.onelittleangel.books.navigation.bookRoute
import com.onelittleangel.books.navigation.booksRoute
import com.onelittleangel.explore.navigation.exploreRoute
import com.onelittleangel.faiths.navigation.faithNameArg
import com.onelittleangel.faiths.navigation.faithRoute
import com.onelittleangel.faiths.navigation.faithsRoute
import com.onelittleangel.foryou.navigation.forYouRoute
import com.onelittleangel.shared.Resources
import com.onelittleangel.themes.navigation.themeNameArg
import com.onelittleangel.themes.navigation.themeRoute
import com.onelittleangel.themes.navigation.themesRoute
import dev.icerock.moko.resources.StringResource

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: StringResource,
    val titleTextId: StringResource,
    val destinations: List<String>,
) {
    EXPLORE(
        selectedIcon = Icons.Filled.Upcoming,
        unselectedIcon = Icons.Outlined.Upcoming,
        iconTextId = Resources.strings.compose_app_explore,
        titleTextId = Resources.strings.compose_app_explore,
        destinations = listOf(exploreRoute, forYouRoute, authorsRoute, booksRoute, faithsRoute, themesRoute,
            "$authorRoute/{$authorNameArg}", "$bookRoute/{$bookNameArg}", "$faithRoute/{$faithNameArg}", "$themeRoute/{$themeNameArg}" )
    ),
    /*RECENTS(
        selectedIcon = Icons.Filled.Schedule,
        unselectedIcon = Icons.Outlined.Schedule,
        iconTextId = Resources.strings.compose_app_recents,
        titleTextId = Resources.strings.compose_app_recents,
        destinations = listOf(recentsRoute)
    ),*/
    BOOKMARKS(
        selectedIcon = Icons.Filled.Bookmarks,
        unselectedIcon = Icons.Outlined.Bookmarks,
        iconTextId = Resources.strings.compose_app_saved,
        titleTextId = Resources.strings.compose_app_saved,
        destinations = listOf(bookmarkRoute, bookmarkGroupsRoute, "$bookmarksRoute/{$bookmarkGroupIdArg}")
    )
}
