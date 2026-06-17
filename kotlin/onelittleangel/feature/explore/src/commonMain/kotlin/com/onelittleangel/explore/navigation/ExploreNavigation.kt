package com.onelittleangel.explore.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.onelittleangel.authors.AuthorRoute
import com.onelittleangel.authors.AuthorsRoute
import com.onelittleangel.authors.navigation.authorNameArg
import com.onelittleangel.authors.navigation.authorRoute
import com.onelittleangel.authors.navigation.authorsRoute
import com.onelittleangel.books.BookRoute
import com.onelittleangel.books.BooksRoute
import com.onelittleangel.books.navigation.bookNameArg
import com.onelittleangel.books.navigation.bookRoute
import com.onelittleangel.books.navigation.booksRoute
import com.onelittleangel.explore.Resources
import com.onelittleangel.faiths.FaithRoute
import com.onelittleangel.faiths.FaithsRoute
import com.onelittleangel.faiths.navigation.faithNameArg
import com.onelittleangel.faiths.navigation.faithRoute
import com.onelittleangel.faiths.navigation.faithsRoute
import com.onelittleangel.foryou.ForYouRoute
import com.onelittleangel.foryou.navigation.forYouRoute
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.themes.ThemeRoute
import com.onelittleangel.themes.ThemesRoute
import com.onelittleangel.themes.navigation.themeNameArg
import com.onelittleangel.themes.navigation.themeRoute
import com.onelittleangel.themes.navigation.themesRoute
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path

var exploreRoute = "/explore_route"
var actualRoute: String = ""

fun Navigator.navigateToExplore(navOptions: NavOptions? = null) {
    this.navigate(actualRoute, navOptions)
}

fun RouteBuilder.exploreGraph(
    shouldShowFilterDialog: MutableState<Boolean>,
    showIndicator: MutableState<Boolean>,
    title: MutableState<String>,
    searchBarModifier: Modifier,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    navigateTo: (FollowableTopic) -> Unit,
    initialRoute: String = forYouRoute,
    onTopicClick: (String) -> Unit,
    state: LazyListState
) {
    group(route = exploreRoute, initialRoute = initialRoute) {
        scene(route = forYouRoute) {
            actualRoute = forYouRoute
            title.value = stringResource(Resources.strings.explore_searchbar_placeholder)

            ForYouRoute(
                onBackClick = {},
                state = state,
                navigateTo = navigateTo,
                onToggleBookmark = onToggleBookmark,
                showIndicator = showIndicator)
        }

        scene(route = authorsRoute) {
            actualRoute = authorsRoute
            title.value = stringResource(Resources.strings.explore_authors)

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            AuthorsRoute(
                onBackClick = {},
                onAuthorClick = navigateTo,
                state = state,
                showIndicator = showIndicator,
                shouldShowAuthorsDialog = shouldShowFilterDialog)
        }

        scene(route = "$authorRoute/{$authorNameArg}") { backStackEntry ->
            actualRoute = "$authorRoute/{$authorNameArg}"
            title.value = authorNameArg

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            backStackEntry.path<String>(authorNameArg)?.let { name ->
                title.value = name
                AuthorRoute(
                    onBackClick = {},
                    name = name,
                    searchBarModifier = searchBarModifier,
                    navigateTo = navigateTo,
                    onTopicClick = navigateTo,
                    state = state,
                    onToggleBookmark = onToggleBookmark,
                    showIndicator = showIndicator)
            }
        }

        scene(route = booksRoute) {
            actualRoute = booksRoute
            title.value = stringResource(Resources.strings.explore_books)

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            BooksRoute(
                onBackClick = {},
                onBookClick = navigateTo,
                state = state,
                showIndicator = showIndicator,
                shouldShowBooksDialog = shouldShowFilterDialog)
        }

        scene(route = "$bookRoute/{$bookNameArg}") { backStackEntry ->
            actualRoute = "$bookRoute/{$bookNameArg}"

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            backStackEntry.path<String>(bookNameArg)?.let { name ->
                title.value = name
                BookRoute(
                    onBackClick = {},
                    name = name,
                    searchBarModifier = searchBarModifier,
                    showIndicator = showIndicator,
                    navigateTo = navigateTo,
                    onTopicClick = navigateTo,
                    state = state,
                    onToggleBookmark = onToggleBookmark)
            }
        }

        scene(route = faithsRoute) {
            actualRoute = faithsRoute
            title.value = stringResource(Resources.strings.explore_faiths)

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            FaithsRoute(
                onBackClick = {},
                onFaithClick = navigateTo,
                state = state,
                showIndicator = showIndicator,
                shouldShowFaithsDialog = shouldShowFilterDialog)
        }

        scene(route = "$faithRoute/{$faithNameArg}") { backStackEntry ->
            actualRoute = "$faithRoute/{$faithNameArg}"

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            backStackEntry.path<String>(faithNameArg)?.let { name ->
                title.value = name

                FaithRoute(
                    onBackClick = {},
                    name = name,
                    searchBarModifier = searchBarModifier,
                    showIndicator = showIndicator,
                    navigateTo = navigateTo,
                    onTopicClick = navigateTo,
                    state = state,
                    onToggleBookmark = onToggleBookmark)
            }
        }

        scene(route = themesRoute) {
            actualRoute = themesRoute
            title.value = stringResource(Resources.strings.explore_themes)

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            ThemesRoute(
                onBackClick = {},
                onThemeClick = navigateTo,
                state = state,
                showIndicator = showIndicator,
                shouldShowThemesDialog = shouldShowFilterDialog)
        }

        scene(route = "$themeRoute/{$themeNameArg}") { backStackEntry ->
            actualRoute = "$themeRoute/{$themeNameArg}"

            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                state.scrollToItem(index = 0)
            }

            backStackEntry.path<String>(themeNameArg)?.let { name ->
                title.value = name

                ThemeRoute(
                    name = name,
                    searchBarModifier = searchBarModifier,
                    showIndicator = showIndicator,
                    navigateTo = navigateTo,
                    onTopicClick = navigateTo,
                    state = state,
                    onToggleBookmark = onToggleBookmark)
            }
        }
    }
}
