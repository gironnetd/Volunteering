package com.onelittleangel.books

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.onelittleangel.designsystem.component.OlaTab
import com.onelittleangel.designsystem.component.OlaTabRow
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.ui.tab.BiographyTabContent
import com.onelittleangel.ui.tab.PicturesTabContent
import com.onelittleangel.ui.tab.QuotesTabContent
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BookRoute(
    state: LazyListState,
    name: String,
    showIndicator: MutableState<Boolean>,
    searchBarModifier: Modifier,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onBackClick: () -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: BookViewModel = koinViewModel(vmClass = BookViewModel::class) { parametersOf(name) },
) {
    val bookUiState: BookUiState by viewModel.bookUiState.collectAsStateWithLifecycle()
    val tabState by viewModel.tabState.collectAsStateWithLifecycle()

    when(bookUiState) {
        BookUiState.Loading -> { showIndicator.value = true }

        is BookUiState.Success -> {
            BookContent(
                tabState = tabState,
                switchTab = viewModel::switchTab,
                bookUiState = bookUiState as BookUiState.Success,
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
private fun BookContent(
    state: LazyListState,
    searchBarModifier: Modifier,
    tabState: BookTabState,
    switchTab: (Int) -> Unit,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    bookUiState: BookUiState.Success,
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

        if(bookUiState.userBook.biography.value != null && bookUiState.userBook.pictures != null) {
            when (tabState.currentIndex) {
                0 -> {
                    QuotesTabContent(
                        quotes = bookUiState.userBook.quotes,
                        onTopicClick =  onTopicClick,
                        state = state,
                        onToggleBookmark = onToggleBookmark,
                        navigateTo = navigateTo
                    )
                }
                1 -> {
                    bookUiState.userBook.biography.value?.let {
                        BiographyTabContent(
                            state = state,
                            biography = mutableStateOf(it),
                            onToggleBookmark = onToggleBookmark,
                            navigateTo = navigateTo
                        )
                    }
                }

                2 -> {
                    bookUiState.userBook.pictures?.let {
                        PicturesTabContent(
                            pictures = it,
                            state = state,
                            onToggleBookmark = onToggleBookmark)
                    }
                }
            }
        }

        if(bookUiState.userBook.biography.value == null && bookUiState.userBook.pictures != null) {
            when (tabState.currentIndex) {
                0 -> {
                    QuotesTabContent(
                        quotes = bookUiState.userBook.quotes,
                        onTopicClick =  onTopicClick,
                        state = state,
                        onToggleBookmark = onToggleBookmark,
                        navigateTo = navigateTo
                    )
                }
                1 -> {
                    bookUiState.userBook.pictures?.let {
                        PicturesTabContent(
                            pictures = it,
                            state = state,
                            onToggleBookmark = onToggleBookmark)
                    }
                }
            }
        }

        if(bookUiState.userBook.biography.value != null && bookUiState.userBook.pictures == null) {
            when (tabState.currentIndex) {
                0 -> {
                    QuotesTabContent(
                        quotes = bookUiState.userBook.quotes,
                        onTopicClick =  onTopicClick,
                        state = state,
                        onToggleBookmark = onToggleBookmark,
                        navigateTo = navigateTo
                    )
                }
                1 -> {
                    bookUiState.userBook.biography.value?.let {
                        BiographyTabContent(
                            state = state,
                            biography = mutableStateOf(it),
                            onToggleBookmark = onToggleBookmark,
                            navigateTo = navigateTo
                        )
                    }
                }
            }
        }
    }
}