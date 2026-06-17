package com.onelittleangel.shared.ui

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.onelittleangel.bookmarks.navigation.navigateToBookmarks
import com.onelittleangel.explore.navigation.navigateToExplore
import com.onelittleangel.shared.navigation.TopLevelDestination
import com.onelittleangel.shared.navigation.TopLevelDestination.BOOKMARKS
import com.onelittleangel.shared.navigation.TopLevelDestination.EXPLORE
import kotlinx.coroutines.CoroutineScope
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun rememberOlaAppState(
    windowSizeClass: WindowSizeClass,
    //networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navigator: Navigator = rememberNavigator()
): NiaAppState {
    //NavigationTrackingSideEffect(navController)
    return remember(navigator, coroutineScope, windowSizeClass, /*networkMonitor*/) {
        NiaAppState(navigator, coroutineScope, windowSizeClass, /*networkMonitor*/)
    }
}

class NiaAppState(
    val navigator: Navigator,
    val coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    //networkMonitor: NetworkMonitor,
) {
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    var shouldShowSettingsDialog by mutableStateOf(false)
        private set

    var shouldShowFilterDialog by mutableStateOf(mutableStateOf(false))
        private set

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = NavOptions(
            /*popUpTo = PopUpTo(
                // The destination of popUpTo
                route = navigator.currentEntry.collectAsState(null).value.id,
                // Whether the popUpTo destination should be popped from the back stack.
                inclusive = true,
            ),*/
            launchSingleTop = false
        )

        when (topLevelDestination) {
            EXPLORE -> navigator.navigateToExplore(topLevelNavOptions)
            //RECENTS -> navigator.navigateToRecents(topLevelNavOptions)
            BOOKMARKS -> navigator.navigateToBookmarks(topLevelNavOptions)
        }
    }

    fun onBackClick() {
        navigator.popBackStack()
    }

    fun setShowSettingsDialog(shouldShow: Boolean) {
        shouldShowSettingsDialog = shouldShow
    }

    fun setShowFilterDialog(shouldShow: Boolean) {
        shouldShowFilterDialog.value = shouldShow
    }
}