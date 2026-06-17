package com.onelittleangel.bookmarks.component.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.onelittleangel.bookmarks.BookmarkGroupsViewModel
import com.onelittleangel.bookmarks.Resources
import com.onelittleangel.model.BookmarkGroup
import com.onelittleangel.model.BookmarkGroupLocation
import com.onelittleangel.ui.util.randomUUID
import dev.icerock.moko.resources.compose.stringResource
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun CreateOrEditBookmarkRoute(
    modifier: Modifier = Modifier,
    onCompleted: () -> Unit,
    viewModel: BookmarkGroupsViewModel = koinViewModel(vmClass = BookmarkGroupsViewModel::class),
) {
    CreateOrEditBookmarkScreen(
        createBookmarkGroup = { viewModel.createBookmarkGroup(it).also { onCompleted() } },
        modifier = modifier
    )
}

@Composable
internal fun CreateOrEditBookmarkScreen(
    modifier: Modifier = Modifier,
    createBookmarkGroup: (BookmarkGroup) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var listName by rememberSaveable { mutableStateOf("") }

        TextField(
            value = listName,
            onValueChange = { listName = it },
            placeholder = {
                Text(
                    stringResource(Resources.strings.bookmarks_list_name),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary)
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape =  MaterialTheme.shapes.small.copy(all = CornerSize(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        var description by rememberSaveable { mutableStateOf("") }

        TextField(
            value = description,
            onValueChange = { description = it },
            placeholder = {
                Text(
                    stringResource(Resources.strings.bookmarks_description),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary)
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            minLines = 8,
            singleLine = false,
            shape =  MaterialTheme.shapes.small.copy(all = CornerSize(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        val undefined = stringResource(Resources.strings.bookmarks_undefined)

        Button(
            onClick = {
                createBookmarkGroup(
                    BookmarkGroup(
                        id = randomUUID(),
                        idParent = null,
                        location = BookmarkGroupLocation.device,
                        directoryName = listName.ifEmpty { undefined },
                        shortDescription = description,
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Resources.strings.bookmarks_create_bookmarks),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(all = 8.dp))
        }

        //Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.ime))
    }
}