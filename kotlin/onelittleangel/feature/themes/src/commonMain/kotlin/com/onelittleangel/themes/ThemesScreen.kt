package com.onelittleangel.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.onelittleangel.common.unaccent
import com.onelittleangel.model.FollowableTopic
import com.onelittleangel.ui.card.ThemeCard
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import kotlin.math.abs

@Composable
fun ThemesRoute(
    state: LazyListState,
    showIndicator: MutableState<Boolean>,
    shouldShowThemesDialog: MutableState<Boolean>,
    onBackClick: () -> Unit,
    onThemeClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: ThemesViewModel = koinViewModel(vmClass = ThemesViewModel::class),
) {
    val themesUiState: ThemesUiState by viewModel.themesUiState.collectAsStateWithLifecycle()

    ThemesScreen(
        themesUiState = themesUiState,
        modifier = modifier,
        onThemeClick = onThemeClick,
        state = state,
        showIndicator = showIndicator,
        showThemesDialog = shouldShowThemesDialog)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ThemesScreen(
    state: LazyListState,
    showIndicator: MutableState<Boolean>,
    showThemesDialog: MutableState<Boolean>,
    themesUiState: ThemesUiState,
    onThemeClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier,
) {
    when(themesUiState) {
        ThemesUiState.Loading -> { showIndicator.value = true }
        is ThemesUiState.Success -> {

            val shouldShowAuthorsDialog = remember { showThemesDialog }
            var filterBy by remember { mutableStateOf("") }

            val scope = rememberCoroutineScope()

            if(shouldShowAuthorsDialog.value) {
                if(filterBy.isNotEmpty()) {
                    filterBy = ""
                    showThemesDialog.value = false

                    scope.launch {
                        state.animateScrollToItem(0)
                    }
                } else {
                    ThemesDialog(
                        themes = themesUiState.userThemes.filter { it.idParentTheme == null },
                        /*faiths = when(booksUiState) {
                            BooksUiState.Loading -> { listOf() }
                            is BooksUiState.Success -> { booksUiState.userBooks }
                        },*/
                        filterBy = { topicName ->
                            filterBy = topicName
                            showThemesDialog.value = false
                        },
                        onDismiss = {
                            shouldShowAuthorsDialog.value = !shouldShowAuthorsDialog.value
                            showThemesDialog.value = false
                        })
                }
            }

            val items = remember { themesUiState.userThemes }
            val headers = remember {
                mutableStateOf(items.map { it.name.unaccent().first().uppercase() }.toSet().toList())
            }

            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = modifier.testTag("themes:feed"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp + SearchBarDefaults.InputFieldHeight.value.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp,
                        end = 16.dp,
                        bottom = 130.dp),
                    state = state
                ) {
                    items.filter { author -> if(filterBy.isEmpty()) author.idParentTheme != null
                    else author.idParentTheme == items.first { it.name == filterBy }.idTheme
                    }.forEach {
                        item(
                            key = it.hashCode()
                        ) {
                            ThemeCard(theme = it, onThemeClick = onThemeClick)
                        }
                    }

                    showIndicator.value = false
                }

                val offsets = remember { mutableStateMapOf<Int, Float>() }
                var selectedHeaderIndex by remember { mutableStateOf(0) }
                val scope = rememberCoroutineScope()

                fun updateSelectedIndexIfNeeded(offset: Float) {
                    val index = offsets
                        .mapValues { abs(it.value - offset) }
                        .entries
                        .minByOrNull { it.value }
                        ?.key ?: return
                    if (selectedHeaderIndex == index) return
                    selectedHeaderIndex = index
                    val selectedItemIndex = items.indexOfFirst { it.name.first().uppercase() == headers.value[selectedHeaderIndex] }
                    scope.launch {
                        state.animateScrollToItem(selectedItemIndex)
                    }
                }

                if(filterBy.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .fillMaxHeight()
                            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp + 16.dp +
                                    SearchBarDefaults.InputFieldHeight.value.dp,
                                bottom = 130.dp,
                                end = 16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .width(34.dp)
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    updateSelectedIndexIfNeeded(it.y)
                                }
                            }
                            .pointerInput(Unit) {
                                detectVerticalDragGestures { change,  _ ->
                                    updateSelectedIndexIfNeeded(change.position.y)
                                }
                            }
                    ) {
                        headers.value.forEachIndexed { i, header ->
                            Text(
                                header,
                                modifier = Modifier.onGloballyPositioned {
                                    offsets[i] = it.boundsInParent().center.y
                                }.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }

    /*LazyColumn(
        modifier = modifier.testTag("themes:feed"),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp + SearchBarDefaults.InputFieldHeight.value.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp,
            end = 16.dp,
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = state
    ) {
        when(themesUiState) {
            ThemesUiState.Loading -> { showIndicator.value = true }

            is ThemesUiState.Success -> {
                themeCards(themesUiState = themesUiState, onThemeClick = onThemeClick)
                showIndicator.value = false
            }
        }
    }*/
}

private fun LazyListScope.themeCards(
    themesUiState: ThemesUiState.Success,
    onThemeClick: (FollowableTopic) -> Unit,
) {
    themesUiState.userThemes.forEach {
        item(
            key = it.hashCode()
        ) {
            ThemeCard(
                theme = it,
                onThemeClick = onThemeClick,
                modifier = Modifier)
        }
    }
}

