package com.onelittleangel.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.model.UserQuote
import com.onelittleangel.ui.card.QuoteCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesTabContent(
    state: LazyListState,
    quotes: SnapshotStateList<UserQuote>,
    navigateTo: (FollowableTopic) -> Unit,
    onTopicClick: (FollowableTopic) -> Unit,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = modifier.testTag("quotes:list"),
        contentPadding = PaddingValues(
            top = (SearchBarDefaults.InputFieldHeight.value.dp * 2 +
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp + 16.dp),
            start = 16.dp,
            end = 16.dp,
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            quotes,
            key =  { it.hashCode() + it.isSaved.hashCode() }
        ) {quote ->
            QuoteCard(quote = quote,
                onTopicClick = onTopicClick,
                navigateTo = navigateTo,
                onToggleBookmark = onToggleBookmark)
        }
    }
}