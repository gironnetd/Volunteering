package com.onelittleangel.themes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.onelittleangel.designsystem.component.OlaTab
import com.onelittleangel.designsystem.component.OlaTabRow
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.ui.tab.PicturesTabContent
import com.onelittleangel.ui.tab.QuotesTabContent
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ThemeRoute(
    state: LazyListState,
    name: String,
    searchBarModifier: Modifier,
    showIndicator: MutableState<Boolean>,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: ThemeViewModel = koinViewModel(vmClass = ThemeViewModel::class) { parametersOf(name) },
) {
    val themeUiState: ThemeUiState by viewModel.themeUiState.collectAsStateWithLifecycle()
    val tabState by viewModel.tabState.collectAsStateWithLifecycle()

    when(themeUiState) {
        ThemeUiState.Loading -> { showIndicator.value = true }

        is ThemeUiState.Success -> {
            ThemeContent(
                tabState = tabState,
                switchTab = viewModel::switchTab,
                themeUiState = themeUiState as ThemeUiState.Success,
                state = state,
                onTopicClick = onTopicClick,
                onToggleBookmark = onToggleBookmark,
                navigateTo = navigateTo,
                searchBarModifier = searchBarModifier
            )

            showIndicator.value = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeContent(
    state: LazyListState,
    tabState: ThemeTabState,
    searchBarModifier: Modifier,
    switchTab: (Int) -> Unit,
    navigateTo: (FollowableTopic) -> Unit,
    themeUiState: ThemeUiState.Success,
    onTopicClick: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
        .padding(top = SearchBarDefaults.InputFieldHeight.value.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp)
) {
    Box {
        OlaTabRow(
            selectedTabIndex = tabState.currentIndex,
            modifier = searchBarModifier
                .zIndex(1f)
                .padding(top = SearchBarDefaults.InputFieldHeight.value.dp +
                        WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp)
        ) {
            tabState.titles.forEachIndexed { index, title ->
                OlaTab(
                    selected = index == tabState.currentIndex,
                    onClick = { switchTab(index) },
                    text = { Text(
                        modifier = Modifier.testTag(stringResource(title)),
                        text = stringResource(title),
                        fontWeight = FontWeight.ExtraBold,)
                    }
                )
            }
        }

        if(themeUiState.userTheme.pictures != null) {
            when (tabState.currentIndex) {
                0 -> {
                    QuotesTabContent(
                        quotes = themeUiState.userTheme.quotes,
                        navigateTo = navigateTo,
                        onTopicClick =  onTopicClick,
                        state = state,
                        onToggleBookmark = onToggleBookmark,
                    )
                }
                1 -> {
                    themeUiState.userTheme.pictures?.let {
                        PicturesTabContent(
                            pictures = it,
                            state = state,
                            onToggleBookmark = onToggleBookmark)
                    }
                }
            }
        }

        if(themeUiState.userTheme.pictures == null) {
            when(tabState.currentIndex) {
                0 -> {
                    QuotesTabContent(
                        quotes = themeUiState.userTheme.quotes,
                        navigateTo = navigateTo,
                        onTopicClick = onTopicClick,
                        state = state,
                        onToggleBookmark = onToggleBookmark
                    )
                }
            }
        }
    }
}