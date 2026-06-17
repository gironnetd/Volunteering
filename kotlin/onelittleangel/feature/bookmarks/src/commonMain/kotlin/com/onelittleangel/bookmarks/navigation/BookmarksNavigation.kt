package com.onelittleangel.bookmarks.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import com.onelittleangel.bookmarks.BookmarkGroupsRoute
import com.onelittleangel.bookmarks.BookmarksRoute
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.BookmarkGroup
import com.onelittleangel.model.FollowableTopic
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query

const val bookmarkRoute = "/bookmark_route"
const val bookmarkGroupsRoute = "/bookmarkGroups_route"
const val bookmarksRoute = "/bookmarks_route"
const val bookmarkGroupIdArg = "bookmarkGroupIdArg"
var actualRoute: String = ""

fun Navigator.navigateToBookmarks(navOptions: NavOptions? = null) {
    this.navigate(actualRoute.ifEmpty { bookmarkGroupsRoute }, navOptions)
}

fun Navigator.navigateToBookmark(bookmarkGroupId: String, directoryName: String, navOptions: NavOptions? = null) {
    this.navigate("$bookmarksRoute/${bookmarkGroupId}?directoryName=$directoryName", navOptions)
}

fun RouteBuilder.bookmarksGraph(
    title: MutableState<String>,
    navigateTo: (FollowableTopic) -> Unit,
    navigateToBookmark: (BookmarkGroup) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    state: LazyListState,
    initialRoute: String = bookmarkGroupsRoute,
) {
    group(route = bookmarkRoute, initialRoute = initialRoute) {
        scene(route = bookmarkGroupsRoute) { backStackEntry ->
            actualRoute = bookmarkGroupsRoute

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            BookmarkGroupsRoute(
                state = state,
                onAuthorClick = {},
                onBookmarkGroupClick = navigateToBookmark
            )
        }

        scene(route = "$bookmarksRoute/{$bookmarkGroupIdArg}") { backStackEntry ->
            actualRoute = "$bookmarksRoute/{$bookmarkGroupIdArg}"

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            backStackEntry.path<String>(bookmarkGroupIdArg)?.let { bookmarkGroupId ->
                backStackEntry.query<String>("directoryName")?.let {
                    title.value = it
                }
                BookmarksRoute(
                    bookmarkGroupId = bookmarkGroupId,
                    state = state,
                    onAuthorClick = {},
                    onBackClick = {},
                    navigateTo = navigateTo,
                    onToggleBookmark = onToggleBookmark)
            }
        }
    }
}