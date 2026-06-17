package com.onelittleangel.faiths

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.onelittleangel.designsystem.component.OlaTab
import com.onelittleangel.designsystem.component.OlaTabRow
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.UserQuote
import com.onelittleangel.ui.tab.BiographyTabContent
import com.onelittleangel.ui.tab.PicturesTabContent
import com.onelittleangel.ui.tab.QuotesTabContent
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun FaithRoute(
    state: LazyListState,
    name: String,
    searchBarModifier: Modifier,
    showIndicator: MutableState<Boolean>,
    navigateTo: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    onBackClick: () -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: FaithViewModel = koinViewModel(vmClass = FaithViewModel::class) { parametersOf(name) },
) {
    val faithUiState: FaithUiState by viewModel.faithUiState.collectAsStateWithLifecycle()
    val tabState by viewModel.tabState.collectAsStateWithLifecycle()

    when(faithUiState) {
        FaithUiState.Loading -> { showIndicator.value = true }

        is FaithUiState.Success -> {
            FaithContent(
                tabState = tabState,
                switchTab = viewModel::switchTab,
                faithUiState = faithUiState as FaithUiState.Success,
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
private fun FaithContent(
    state: LazyListState,
    searchBarModifier: Modifier,
    tabState: FaithTabState,
    switchTab: (Int) -> Unit,
    faithUiState: FaithUiState.Success,
    navigateTo: (FollowableTopic) -> Unit,
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

        if(faithUiState.userFaith.biography.value != null && faithUiState.userFaith.pictures != null) {
            when (tabState.currentIndex) {
                0 -> {
                    val quotes: SnapshotStateList<UserQuote> = mutableStateListOf<UserQuote>().apply {
                        faithUiState.userFaith.authors?.flatMap { it.quotes }
                            ?.let { this.addAll(it) }
                        faithUiState.userFaith.books?.flatMap { it.quotes }
                    }

                    QuotesTabContent(
                        quotes = quotes,
                        onTopicClick =  onTopicClick,
                        state = state,
                        onToggleBookmark = onToggleBookmark,
                        navigateTo = navigateTo
                    )
                }
                1 -> {
                    faithUiState.userFaith.biography.value?.let {
                        BiographyTabContent(
                            state = state,
                            biography = mutableStateOf(it),
                            onToggleBookmark = onToggleBookmark,
                            navigateTo = navigateTo
                        )
                    }
                }

                2 -> {
                    faithUiState.userFaith.pictures?.let {
                        PicturesTabContent(
                            pictures = it,
                            state = state,
                            onToggleBookmark = onToggleBookmark)
                    }
                }
            }
        }

        if(faithUiState.userFaith.biography.value == null && faithUiState.userFaith.pictures != null) {
            when (tabState.currentIndex) {
                0 -> {
                    val quotes: SnapshotStateList<UserQuote> = mutableStateListOf<UserQuote>().apply {
                        faithUiState.userFaith.authors?.flatMap { it.quotes }
                            ?.let { this.addAll(it) }
                        faithUiState.userFaith.books?.flatMap { it.quotes }
                    }

                    QuotesTabContent(
                        quotes = quotes,
                        onTopicClick =  onTopicClick,
                        state = state,
                        onToggleBookmark = onToggleBookmark,
                        navigateTo = navigateTo
                    )
                }
                1 -> {
                    faithUiState.userFaith.pictures?.let {
                        PicturesTabContent(
                            pictures = it,
                            state = state,
                            onToggleBookmark = onToggleBookmark)
                    }
                }
            }
        }

        if(faithUiState.userFaith.biography.value != null && faithUiState.userFaith.pictures == null) {
            when (tabState.currentIndex) {
                0 -> {
                    val quotes: SnapshotStateList<UserQuote> = mutableStateListOf<UserQuote>().apply {
                        faithUiState.userFaith.authors?.flatMap { it.quotes }
                            ?.let { this.addAll(it) }
                        faithUiState.userFaith.books?.flatMap { it.quotes }
                    }

                    QuotesTabContent(
                        quotes = quotes,
                        onTopicClick =  onTopicClick,
                        state = state,
                        onToggleBookmark = onToggleBookmark,
                        navigateTo = navigateTo
                    )
                }
                1 -> {
                    faithUiState.userFaith.biography.value?.let {
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