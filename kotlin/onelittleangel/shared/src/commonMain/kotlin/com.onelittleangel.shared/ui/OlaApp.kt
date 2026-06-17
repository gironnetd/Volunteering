package com.onelittleangel.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.onelittleangel.authors.navigation.authorsRoute
import com.onelittleangel.bookmarks.component.sheet.CreateOrEditBookmarkRoute
import com.onelittleangel.bookmarks.component.sheet.SaveBookmarkRoute
import com.onelittleangel.books.navigation.booksRoute
import com.onelittleangel.designsystem.component.OlaGradientBackground
import com.onelittleangel.designsystem.component.OlaNavigationBar
import com.onelittleangel.designsystem.component.OlaNavigationBarItem
import com.onelittleangel.designsystem.component.OlaNavigationRail
import com.onelittleangel.designsystem.component.OlaNavigationRailItem
import com.onelittleangel.designsystem.component.ScrollDirection
import com.onelittleangel.designsystem.component.rememberDirectionalLazyListState
import com.onelittleangel.designsystem.icon.OlaIcons
import com.onelittleangel.designsystem.theme.OlaTheme
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.faiths.navigation.faithsRoute
import com.onelittleangel.foryou.navigation.forYouRoute
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.DarkThemeConfig
import com.onelittleangel.model.ThemeBrand
import com.onelittleangel.settings.SettingsDialog
import com.onelittleangel.shared.MainActivityUiState
import com.onelittleangel.shared.MainActivityViewModel
import com.onelittleangel.shared.Resources
import com.onelittleangel.shared.navigation.OlaNavHost
import com.onelittleangel.shared.navigation.TopLevelDestination
import com.onelittleangel.themes.navigation.themesRoute
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.route.Route
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinContext
import kotlin.math.roundToInt

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun OlaApp() {
    PreComposeApp {
        KoinContext {
            val appState: NiaAppState = rememberOlaAppState(calculateWindowSizeClass())
            val lazyListState = rememberLazyListState()
            val directionalLazyListState = rememberDirectionalLazyListState(lazyListState)
            val title = stringResource(com.onelittleangel.explore.Resources.strings.explore_searchbar_placeholder)
            val placeholder = remember { mutableStateOf(title) }

            val hideNavigationBar = remember { mutableStateOf(true) }

            if(directionalLazyListState.scrollDirection == ScrollDirection.Down) {
                hideNavigationBar.value = true
            } else if(directionalLazyListState.scrollDirection == ScrollDirection.Up) {
                hideNavigationBar.value = false
            }

            val navigationBarHeight = NavigationBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding().value.dp
            val localDensity = LocalDensity.current

            val sheetState = remember {
                ModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    density = localDensity,
                    isSkipHalfExpanded = true
                )
            }

            val showSaveBookmark = remember { mutableStateOf(false) }
            val showCreateBookmarkGroup = remember { mutableStateOf(false) }
            val showIndicator = remember { mutableStateOf(false) }

            var bookmarkToSave by remember { mutableStateOf<Bookmark?>(null) }

            val viewModel: MainActivityViewModel = koinViewModel(vmClass = MainActivityViewModel::class)
            val uiState: MainActivityUiState by viewModel.uiState.collectAsStateWithLifecycle()

            val bottomBarHeight = 130.dp
            val bottomBarHeightPx = with(LocalDensity.current) {
                bottomBarHeight.roundToPx().toFloat() + navigationBarHeight.roundToPx().toFloat() }
            val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }

            val searchBarHeight = 130.dp
            val searchBarHeightPx = with(LocalDensity.current) {
                searchBarHeight.roundToPx().toFloat() + navigationBarHeight.roundToPx().toFloat() }
            val searchBarOffsetHeightPx = remember { mutableStateOf(0f) }

            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                        // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                        val delta = available.y
                        val newOffsetBottomBar = bottomBarOffsetHeightPx.value + delta
                        val newOffsetSearchBar = searchBarOffsetHeightPx.value + delta
                        bottomBarOffsetHeightPx.value = newOffsetBottomBar.coerceIn(-bottomBarHeightPx, 0f)
                        searchBarOffsetHeightPx.value = newOffsetSearchBar.coerceIn(-searchBarHeightPx, 0f)
                        // here's the catch: let's pretend we consumed 0 in any case, since we want
                        // LazyColumn to scroll anyway for good UX
                        // We're basically watching scroll without taking it
                        return Offset.Zero
                    }
                }
            }

            val scope = rememberCoroutineScope()

            OlaTheme(
                darkTheme = shouldUseDarkTheme(uiState),
                themeBrand = shouldUseThemeBrand(uiState)
            ) {
                ModalBottomSheetLayout(
                    sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                    sheetContentColor = if(PaletteTokens.LocalSystemInDarkTheme.current)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        PaletteTokens.White,
                    sheetState = sheetState,
                    sheetContent = {
                        if(showSaveBookmark.value) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                                    .background(
                                        color = if(PaletteTokens.LocalSystemInDarkTheme.current)
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            PaletteTokens.White)
                                    .imePadding()
                            ) {
                                when(sheetState.currentValue) {
                                    ModalBottomSheetValue.Hidden -> {
                                        if(showCreateBookmarkGroup.value) {
                                            scope.launch {
                                                showSaveBookmark.value = true
                                                showCreateBookmarkGroup.value = false
                                                sheetState.show()
                                            }
                                        }
                                    }
                                    ModalBottomSheetValue.Expanded -> {
                                        if(showCreateBookmarkGroup.value) {
                                            CreateOrEditBookmarkRoute(
                                                onCompleted = { showCreateBookmarkGroup.value = false },
                                                modifier = Modifier
                                                    .padding(bottom = if(WindowInsets.ime.getBottom(LocalDensity.current) > 0) 16.dp else
                                                    navigationBarHeight)
                                                    .padding(top = 16.dp),
                                            )
                                        } else {
                                            bookmarkToSave?.let { bookmarkToSave ->
                                                SaveBookmarkRoute(
                                                    bookmark = bookmarkToSave,
                                                    showCreateBookmarkGroup = { showCreateBookmarkGroup.value = true },
                                                    modifier = Modifier.padding(bottom = navigationBarHeight)
                                                        .padding(top = 32.dp),
                                                )
                                            }
                                        }

                                        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.ime))
                                    }
                                    ModalBottomSheetValue.HalfExpanded -> {}
                                }
                            }
                        }
                    },
                    content = {
                        Scaffold(
                            modifier = Modifier.nestedScroll(nestedScrollConnection),
                            contentWindowInsets = WindowInsets(0, 0, 0, 0),
                            bottomBar = {
                                if(appState.shouldShowBottomBar) {
                                    OlaBottomBar(
                                        destinations = appState.topLevelDestinations,
                                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                                        navigator = appState.navigator,
                                        modifier = Modifier
                                            .offset { IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt()) },
                                    )
                                }
                            }
                        ) { _ ->
                            OlaGradientBackground {
                                Column(Modifier.fillMaxWidth()) {
                                    when(uiState) {
                                        MainActivityUiState.Loading -> {}
                                        is MainActivityUiState.Success -> {
                                            Flowers(uiState as MainActivityUiState.Success)
                                        }
                                    }
                                }

                                var text by rememberSaveable { mutableStateOf("") }
                                var active by rememberSaveable { mutableStateOf(false) }

                                val navRailWidth = remember { mutableStateOf(0.dp) }

                                Row(
                                    Modifier
                                        .fillMaxSize()
                                        .windowInsetsPadding(
                                            WindowInsets.safeDrawing.only(
                                                WindowInsetsSides.Horizontal
                                            )
                                        )
                                ) {
                                    if (appState.shouldShowNavRail) {
                                        OlaNavRail(
                                            destinations = appState.topLevelDestinations,
                                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                                            navigator = appState.navigator,
                                            modifier = Modifier.onGloballyPositioned { coordinates ->
                                                navRailWidth.value = coordinates.size.width.dp
                                            }
                                        )
                                    }

                                    OlaNavHost(
                                        title = placeholder,
                                        showIndicator = showIndicator,
                                        searchBarModifier = Modifier
                                            .offset {
                                                IntOffset(x = 0, y = searchBarOffsetHeightPx.value.roundToInt())
                                            },
                                        onToggleBookmark = { bookmark, _ ->
                                            bookmarkToSave = bookmark
                                            showSaveBookmark.value = true

                                            scope.launch { sheetState.show() }
                                        },
                                        state = lazyListState,
                                        navigator = appState.navigator,
                                        shouldShowFilterDialog = appState.shouldShowFilterDialog
                                    )
                                }

                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp,
                                            start = if (appState.shouldShowNavRail) (navRailWidth.value / 2 + 16.dp) else 16.dp,
                                            end = 16.dp)
                                        .offset { IntOffset(x = 0, y = searchBarOffsetHeightPx.value.roundToInt()) }
                                ) {
                                    DockedSearchBar(
                                        modifier = Modifier.fillMaxWidth(),
                                        query = text,
                                        onQueryChange = { text = it },
                                        onSearch = { active = false },
                                        active = active,
                                        onActiveChange = { active = it },
                                        placeholder = {
                                            Text(
                                                placeholder.value,
                                                modifier = Modifier.testTag("searchBar:placeholder"),
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                style = MaterialTheme.typography.bodyMedium,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                                imageVector = if(appState.navigator.canGoBack.collectAsState(initial = true).value) {
                                                    Icons.AutoMirrored.Filled.ArrowBack
                                                } else {
                                                    Icons.Filled.Search
                                                },
                                                contentDescription = "Localized description",
                                                modifier = Modifier.clickable { appState.onBackClick() }
                                            )
                                        },
                                        trailingIcon = {
                                            Row {
                                                if((placeholder.value == stringResource(com.onelittleangel.explore.Resources.strings.explore_authors) ||
                                                    placeholder.value == stringResource(com.onelittleangel.explore.Resources.strings.explore_books) ||
                                                    placeholder.value == stringResource(com.onelittleangel.explore.Resources.strings.explore_faiths) ||
                                                    placeholder.value == stringResource(com.onelittleangel.explore.Resources.strings.explore_themes)) &&
                                                    appState.navigator.currentEntry.collectAsState(null).value?.route.isTopLevelDestinationInHierarchy(TopLevelDestination.EXPLORE)) {
                                                    Icon(tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                                        imageVector = Icons.Filled.FilterList,
                                                        contentDescription = "Localized description",
                                                        modifier = Modifier.clickable { appState.setShowFilterDialog(true) }
                                                    )
                                                }

                                                Icon(tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    imageVector = Icons.Filled.MoreVert,
                                                    contentDescription = "Localized description",
                                                    modifier = Modifier.clickable { appState.setShowSettingsDialog(true) }
                                                )
                                            }
                                        },
                                        colors = SearchBarDefaults.colors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
                                        ),
                                    ) {
                                        LazyColumn(
                                            modifier = Modifier.fillMaxWidth()
                                                .windowInsetsPadding(
                                                    WindowInsets.safeDrawing.only(
                                                        WindowInsetsSides.Horizontal
                                                    )
                                                ),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            val tabs = listOf(
                                                Pair(Resources.strings.compose_app_foryou, forYouRoute),
                                                Pair(Resources.strings.compose_app_authors, authorsRoute),
                                                Pair(Resources.strings.compose_app_books, booksRoute),
                                                Pair(Resources.strings.compose_app_faiths, faithsRoute),
                                                Pair(Resources.strings.compose_app_themes, themesRoute))

                                            items(tabs) { tab ->
                                                ListItem(
                                                    modifier = Modifier.clickable {
                                                        active = false
                                                        appState.navigator.navigate(tab.second)
                                                    },
                                                    headlineContent = {
                                                        Text(
                                                            modifier = Modifier.testTag(stringResource(tab.first)),
                                                            text = stringResource(tab.first),
                                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                            style = MaterialTheme.typography.bodyMedium,
                                                        )
                                                    },
                                                    leadingContent = {
                                                        Icon(
                                                            imageVector = Icons.Filled.Schedule,
                                                            contentDescription = null,
                                                            tint = MaterialTheme.colorScheme.onPrimaryContainer) },
                                                    colors = ListItemDefaults.colors(
                                                        containerColor = Color.Transparent
                                                    )
                                                )
                                            }
                                        }
                                    }

                                    if(showIndicator.value) {
                                        LinearProgressIndicator(
                                            color = MaterialTheme.colorScheme.primary,
                                            trackColor = Color.Transparent,
                                            strokeCap = StrokeCap.Round,
                                            modifier = Modifier
                                                .offset(y = (-5).dp)
                                                .zIndex(1f)
                                                .fillMaxWidth().padding(horizontal = 20.dp),
                                        )
                                    }
                                }

                                if (appState.shouldShowSettingsDialog) {
                                    SettingsDialog(
                                        onDismiss = { appState.setShowSettingsDialog(false) }
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun OlaNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    OlaNavigationRail(modifier = modifier) {
        Spacer(Modifier.weight(1f))
        destinations.forEach { destination ->
            val selected = navigator.currentEntry.collectAsState(null).value?.route.isTopLevelDestinationInHierarchy(destination)
            OlaNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    if (selected) {
                        Icon(tint = MaterialTheme.colorScheme.primary,
                            imageVector = destination.selectedIcon,
                            contentDescription = null)
                    } else {
                        Icon(tint = MaterialTheme.colorScheme.primary,
                            imageVector = destination.unselectedIcon,
                            contentDescription = null)
                    }
                },
                label = { Text(stringResource(destination.iconTextId) , color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall) }
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun OlaBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    navigator: Navigator,
    modifier: Modifier
) {
    OlaNavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = navigator.currentEntry.collectAsState(null).value?.route.isTopLevelDestinationInHierarchy(destination)
            OlaNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    if (selected) {
                        Icon(tint = MaterialTheme.colorScheme.primary,
                            imageVector = destination.selectedIcon,
                            contentDescription = null)
                    } else {
                        Icon(tint = MaterialTheme.colorScheme.primary,
                            imageVector = destination.unselectedIcon,
                            contentDescription = null)
                    }
                },
                label = { Text(stringResource(destination.iconTextId) , color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall) }
            )
        }
    }
}

private fun Route?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination): Boolean {
    return destination.destinations.contains(this?.route)
}

@Composable
private fun shouldUseThemeBrand(
    uiState: MainActivityUiState,
): ThemeBrand = when (uiState) {
    MainActivityUiState.Loading -> ThemeBrand.primary
    is MainActivityUiState.Success -> uiState.userData.themeBrand
}

/**
 * Returns `true` if dark theme should be used, as a function of the [uiState] and the
 * current system context.
 */
@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.userData.darkThemeConfig) {
        DarkThemeConfig.systemDefault -> isSystemInDarkTheme()
        DarkThemeConfig.light -> false
        DarkThemeConfig.dark -> true
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Flowers(
    uiState: MainActivityUiState.Success,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        when(uiState.userData.themeBrand) {
            ThemeBrand.primary -> {
                if(shouldUseDarkTheme(uiState)) {
                    OlaIcons.DarkPrimaryFlowersLeft()
                    OlaIcons.DarkPrimaryFlowersRight()
                } else {
                    OlaIcons.PrimaryFlowersLeft()
                    OlaIcons.PrimaryFlowersRight()
                }
            }
            ThemeBrand.secondary -> {
                if(shouldUseDarkTheme(uiState)) {
                    OlaIcons.DarkSecondaryFlowersLeft()
                    OlaIcons.DarkSecondaryFlowersRight()
                } else {
                    OlaIcons.SecondaryFlowersLeft()
                    OlaIcons.SecondaryFlowersRight()
                }
            }
            ThemeBrand.tertiary -> {
                if(shouldUseDarkTheme(uiState)) {
                    OlaIcons.DarkTertiaryFlowersLeft()
                    OlaIcons.DarkTertiaryFlowersRight()
                } else {
                    OlaIcons.TertiaryFlowersLeft()
                    OlaIcons.TertiaryFlowersRight()
                }
            }
            ThemeBrand.quaternary -> {
                if(shouldUseDarkTheme(uiState)) {
                    OlaIcons.DarkQuaternaryFlowersLeft()
                    OlaIcons.DarkQuaternaryFlowersRight()
                } else {
                    OlaIcons.QuaternaryFlowersLeft()
                    OlaIcons.QuaternaryFlowersRight()
                }
            }
        }
    }
}
