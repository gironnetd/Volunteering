package com.onelittleangel.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.onelittleangel.model.UserPicture
import com.onelittleangel.ui.card.PictureCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicturesTabContent(
    state: LazyListState,
    pictures: SnapshotStateList<UserPicture>,
    onToggleBookmark: (Bookmark, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = Modifier.fillMaxSize().testTag("pictures:list"),
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
            pictures,
            key = { it.hashCode() + it.isSaved.hashCode() }
        ) {picture ->
            PictureCard(picture = picture, onToggleBookmark = onToggleBookmark)
        }
    }
}