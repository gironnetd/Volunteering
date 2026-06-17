package com.onelittleangel.authors

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun AuthorRoute(
    state: LazyListState,
    name: String,
    showIndicator: MutableState<Boolean>,
    searchBarModifier: Modifier,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    navigateTo: (FollowableTopic) -> Unit,
    onBackClick: () -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: AuthorViewModel = koinViewModel(vmClass = AuthorViewModel::class) { parametersOf(name) },
) {
    val authorUiState: AuthorUiState by viewModel.authorUiState.collectAsStateWithLifecycle()
    val tabState by viewModel.tabState.collectAsStateWithLifecycle()

    when(authorUiState) {
        AuthorUiState.Loading -> { showIndicator.value = true }

        is AuthorUiState.Success -> {
            AuthorContent(
                tabState = tabState,
                switchTab = viewModel::switchTab,
                authorUiState = authorUiState as AuthorUiState.Success,
                onTopicClick = onTopicClick,
                onToggleBookmark = onToggleBookmark,
                state = state,
                navigateTo = navigateTo,
                searchBarModifier = searchBarModifier
            )

            showIndicator.value = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuthorContent(
    state: LazyListState,
    tabState: AuthorTabState,
    searchBarModifier: Modifier,
    switchTab: (Int) -> Unit,
    authorUiState: AuthorUiState.Success,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
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

        if(authorUiState.userAuthor.biography.value != null && authorUiState.userAuthor.pictures != null) {
            when (tabState.currentIndex) {
                0 -> {
                    QuotesTabContent(
                        quotes = authorUiState.userAuthor.quotes,
                        onTopicClick =  onTopicClick,
                        onToggleBookmark = onToggleBookmark,
                        state = state,
                        navigateTo = navigateTo
                    )
                }
                1 -> {
                    authorUiState.userAuthor.biography.value?.let {
                        BiographyTabContent(
                            state = state,
                            biography = mutableStateOf(it),
                            onToggleBookmark = onToggleBookmark,
                            navigateTo = navigateTo
                        )
                    }
                }
                2 -> {
                    authorUiState.userAuthor.pictures?.let {
                        PicturesTabContent(
                            pictures = it,
                            modifier = Modifier.fillMaxWidth(),
                            state = state,
                            onToggleBookmark = onToggleBookmark)
                    }
                }
            }
        }

        if(authorUiState.userAuthor.biography.value == null && authorUiState.userAuthor.pictures != null) {
            when (tabState.currentIndex) {
                0 -> {
                    QuotesTabContent(
                        quotes = authorUiState.userAuthor.quotes,
                        navigateTo = navigateTo,
                        onTopicClick = onTopicClick,
                        onToggleBookmark = onToggleBookmark,
                        state = state
                    )
                }
                1 -> {
                    authorUiState.userAuthor.pictures?.let {
                        PicturesTabContent(
                            pictures = it,
                            state = state,
                            onToggleBookmark = onToggleBookmark)
                    }
                }
            }
        }

        if(authorUiState.userAuthor.biography.value != null && authorUiState.userAuthor.pictures == null) {
            when (tabState.currentIndex) {
                0 -> {
                    QuotesTabContent(
                        quotes = authorUiState.userAuthor.quotes,
                        navigateTo = navigateTo,
                        onTopicClick = onTopicClick,
                        onToggleBookmark = onToggleBookmark,
                        state = state
                    )
                }
                1 -> {
                    authorUiState.userAuthor.biography.value?.let {
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