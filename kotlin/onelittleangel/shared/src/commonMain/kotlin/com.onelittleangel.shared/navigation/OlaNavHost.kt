package com.onelittleangel.shared.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.onelittleangel.authors.navigation.navigateToAuthor
import com.onelittleangel.bookmarks.navigation.bookmarksGraph
import com.onelittleangel.bookmarks.navigation.navigateToBookmark
import com.onelittleangel.books.navigation.navigateToBook
import com.onelittleangel.explore.navigation.exploreGraph
import com.onelittleangel.explore.navigation.exploreRoute
import com.onelittleangel.faiths.navigation.navigateToFaith
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.ResourceKind
import com.onelittleangel.recents.navigation.recentsScreen
import com.onelittleangel.themes.navigation.navigateToTheme
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun OlaNavHost(
    showIndicator: MutableState<Boolean>,
    shouldShowFilterDialog: MutableState<Boolean>,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    searchBarModifier: Modifier,
    title:  MutableState<String>,
    navigator: Navigator,
    state: LazyListState,
    initialRoute: String = exploreRoute
) {
    NavHost(
        navigator = navigator,
        initialRoute = initialRoute,
        modifier = Modifier
    ) {
        exploreGraph(
            navigateTo = {
                when(it.topic.kind) {
                    ResourceKind.author -> navigator.navigateToAuthor(name = it.topic.name)
                    ResourceKind.book -> navigator.navigateToBook(name = it.topic.name)
                    ResourceKind.movement -> navigator.navigateToFaith(name = it.topic.name)
                    ResourceKind.theme -> navigator.navigateToTheme(name = it.topic.name)
                    else -> { }
                }
            },
            onTopicClick = { },
            state = state,
            title = title,
            showIndicator = showIndicator,
            searchBarModifier = searchBarModifier,
            onToggleBookmark = onToggleBookmark,
            shouldShowFilterDialog = shouldShowFilterDialog
        )
        recentsScreen(onTopicClick = { })
        bookmarksGraph(
            navigateTo = {
                when(it.topic.kind) {
                    ResourceKind.author -> navigator.navigateToAuthor(name = it.topic.name)
                    ResourceKind.book -> navigator.navigateToBook(name = it.topic.name)
                    ResourceKind.movement -> navigator.navigateToFaith(name = it.topic.name)
                    ResourceKind.theme -> navigator.navigateToTheme(name = it.topic.name)
                    else -> { }
                }
            },
            navigateToBookmark = {
                navigator.navigateToBookmark(bookmarkGroupId = it.id, directoryName = it.directoryName) },
            onTopicClick = { },
            state = state,
            onToggleBookmark = onToggleBookmark,
            title = title)
    }
}
