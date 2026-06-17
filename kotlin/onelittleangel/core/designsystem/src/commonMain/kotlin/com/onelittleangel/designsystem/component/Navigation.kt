package com.onelittleangel.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.theme.PaletteTokens

@Composable
fun RowScope.OlaNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        //modifier = modifier.padding(paddingValues = PaddingValues(bottom = NavigationBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding().value.dp)),
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = OlaNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = OlaNavigationDefaults.navigationContentColor(),
            selectedTextColor = OlaNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = OlaNavigationDefaults.navigationContentColor(),
            indicatorColor = OlaNavigationDefaults.navigationIndicatorColor()
        )
    )
}

@Composable
fun OlaNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = OlaNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        //windowInsets = WindowInsets(bottom = NavigationBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding().value.dp),
        content = content
    )
}

@Composable
fun OlaNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = OlaNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = OlaNavigationDefaults.navigationContentColor(),
            selectedTextColor = OlaNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = OlaNavigationDefaults.navigationContentColor(),
            indicatorColor = OlaNavigationDefaults.navigationIndicatorColor()
        )
    )
}

/**
 * Now in Android navigation rail with header and content slots. Wraps Material 3 [NavigationRail].
 *
 * @param modifier Modifier to be applied to the navigation rail.
 * @param header Optional header that may hold a floating action button or a logo.
 * @param content Destinations inside the navigation rail. This should contain multiple
 * [NavigationRailItem]s.
 */
@Composable
fun OlaNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = OlaNavigationDefaults.navigationContentColor(),
        header = header,
        content = content
    )
}

object OlaNavigationDefaults {
    @Composable
    fun navigationContentColor() = if(PaletteTokens.LocalSystemInDarkTheme.current)
        MaterialTheme.colorScheme.onPrimary
    else
        PaletteTokens.White

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer
    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}