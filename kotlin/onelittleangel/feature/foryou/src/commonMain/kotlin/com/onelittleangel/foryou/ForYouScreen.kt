package com.onelittleangel.foryou

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.onelittleangel.foryou.component.ForYouBiographyCard
import com.onelittleangel.foryou.component.ForYouPictureCard
import com.onelittleangel.foryou.component.ForYouQuoteCard
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun ForYouRoute(
    //showBottomSheet: MutableState<Boolean>,
    state: LazyListState,
    showIndicator: MutableState<Boolean>,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    navigateTo: (FollowableTopic) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForYouViewModel = koinViewModel(vmClass = ForYouViewModel::class),
) {
    val forYouUiState: ForYouUiState by viewModel.forYouUiState.collectAsStateWithLifecycle()

    ForYouScreen(
        forYouUiState = forYouUiState,
        onTopicClick = {},
        state = state,
        onToggleBookmark = onToggleBookmark,
        navigateTo = navigateTo,
        showIndicator = showIndicator
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ForYouScreen(
    state: LazyListState,
    showIndicator: MutableState<Boolean>,
    forYouUiState: ForYouUiState,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = state,
        modifier = modifier.testTag("forYou:feed"),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp + SearchBarDefaults.InputFieldHeight.value.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp,
            end = 16.dp,
            bottom = 130.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        when(forYouUiState) {
            ForYouUiState.Loading -> { showIndicator.value = true }

            is ForYouUiState.Success -> {
                forYouQuotesCards(
                    forYouUiState = forYouUiState,
                    onTopicClick = onTopicClick,
                    onToggleBookmark = onToggleBookmark,
                    navigateTo = navigateTo)

                forYouPicturesCards(
                    forYouUiState = forYouUiState,
                    onTopicClick = onTopicClick,
                    onToggleBookmark = onToggleBookmark,
                    navigateTo = navigateTo)

                forYouBiographiesCards(
                    forYouUiState = forYouUiState,
                    onTopicClick = onTopicClick,
                    onToggleBookmark = onToggleBookmark,
                    navigateTo = navigateTo)

                showIndicator.value = false
            }
        }
    }
}

private fun LazyListScope.forYouQuotesCards(
    forYouUiState: ForYouUiState.Success,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
) {
    items(
        forYouUiState.forYou.first,
        key =  { it.hashCode() + it.isSaved.hashCode()  }
    ) { quote ->
        ForYouQuoteCard(
            quote = quote,
            onTopicClick = onTopicClick,
            onToggleBookmark = onToggleBookmark,
            navigateTo = navigateTo)
    }
}

private fun LazyListScope.forYouPicturesCards(
    forYouUiState: ForYouUiState.Success,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
) {
    items(
        forYouUiState.forYou.second,
        key =  { it.hashCode() + it.isSaved.hashCode() }
    ) { picture ->
        ForYouPictureCard(
            picture = picture,
            onTopicClick = onTopicClick,
            onToggleBookmark = onToggleBookmark,
            navigateTo = navigateTo)
    }
}

private fun LazyListScope.forYouBiographiesCards(
    forYouUiState: ForYouUiState.Success,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
) {
    items(
        forYouUiState.forYou.third,
        key =  { it.hashCode() + it.isSaved.hashCode() }
    ) { biography ->
        ForYouBiographyCard(
            biography = biography,
            onTopicClick = onTopicClick,
            onToggleBookmark = onToggleBookmark,
            navigateTo = navigateTo)
    }
}