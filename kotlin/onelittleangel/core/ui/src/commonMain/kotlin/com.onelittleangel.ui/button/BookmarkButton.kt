package com.onelittleangel.ui.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.onelittleangel.designsystem.component.OlaIconToggleButton
import com.onelittleangel.designsystem.icon.OlaIcons

@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    OlaIconToggleButton(
        //enabled = false,
        checked = isBookmarked,
        onCheckedChange = {
            onClick(it)
        },
        modifier = modifier,
        icon = {
            if(isBookmarked) { OlaIcons.Bookmark() } else { OlaIcons.BookmarkBorder() }
        },
        checkedIcon = {
            if(isBookmarked) { OlaIcons.Bookmark() } else { OlaIcons.BookmarkBorder() }
        },
    )
}

