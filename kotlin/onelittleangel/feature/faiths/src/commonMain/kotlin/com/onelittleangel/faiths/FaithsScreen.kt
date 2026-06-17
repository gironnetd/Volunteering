package com.onelittleangel.faiths

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
import com.onelittleangel.ui.card.FaithCard
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import kotlin.math.abs

@Composable
fun FaithsRoute(
    state: LazyListState,
    onBackClick: () -> Unit,
    showIndicator: MutableState<Boolean>,
    shouldShowFaithsDialog: MutableState<Boolean>,
    onFaithClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: FaithsViewModel = koinViewModel(vmClass = FaithsViewModel::class),
) {
    val faithsUiState: FaithsUiState by viewModel.faithsUiState.collectAsStateWithLifecycle()

    FaithsScreen(
        faithsUiState = faithsUiState,
        modifier = modifier,
        onFaithClick = onFaithClick,
        state = state,
        showIndicator = showIndicator,
        showFaithsDialog = shouldShowFaithsDialog
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FaithsScreen(
    state: LazyListState,
    showIndicator: MutableState<Boolean>,
    showFaithsDialog: MutableState<Boolean>,
    faithsUiState: FaithsUiState,
    onFaithClick: (FollowableTopic) -> Unit,
    modifier: Modifier = Modifier,
) {
    when(faithsUiState) {
        FaithsUiState.Loading -> { showIndicator.value = true }
        is FaithsUiState.Success -> {

            val shouldShowAuthorsDialog = remember { showFaithsDialog }
            var filterBy by remember { mutableStateOf("") }

            val scope = rememberCoroutineScope()

            if(shouldShowAuthorsDialog.value) {
                if(filterBy.isNotEmpty()) {
                    filterBy = ""
                    showFaithsDialog.value = false

                    scope.launch {
                        state.animateScrollToItem(0)
                    }
                } else {
                    FaithsDialog(
                        faiths = faithsUiState.userFaiths.filter { it.idParentMovement == null },
                        /*faiths = when(booksUiState) {
                            BooksUiState.Loading -> { listOf() }
                            is BooksUiState.Success -> { booksUiState.userBooks }
                        },*/
                        filterBy = { topicName ->
                            filterBy = topicName
                            showFaithsDialog.value = false
                        },
                        onDismiss = {
                            shouldShowAuthorsDialog.value = !shouldShowAuthorsDialog.value
                            showFaithsDialog.value = false
                        })
                }
            }

            val items = remember { faithsUiState.userFaiths }
            val headers = remember {
                mutableStateOf(items.map { it.name.unaccent().first().uppercase() }.toSet().toList())
            }

            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = modifier.testTag("faiths:feed"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp + SearchBarDefaults.InputFieldHeight.value.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp,
                        end = 16.dp,
                        bottom = 130.dp),
                    state = state
                ) {
                    items.filter { author -> if(filterBy.isEmpty()) author.idParentMovement != null
                    else author.idParentMovement == items.first { it.name == filterBy }.idMovement
                    }.forEach {
                        item(
                            key = it.hashCode()
                        ) {
                            FaithCard(faith = it, onFaithClick = onFaithClick)
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
        modifier = modifier.testTag("faiths:feed"),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp + SearchBarDefaults.InputFieldHeight.value.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.dp,
            end = 16.dp,
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = state
    ) {
        when (faithsUiState) {
            FaithsUiState.Loading -> { showIndicator.value = true }
            is FaithsUiState.Success -> {
                faithCards(faithsUiState = faithsUiState, onFaithClick = onFaithClick)
                showIndicator.value = false
            }
        }
    }*/
}

private fun LazyListScope.faithCards(
    faithsUiState: FaithsUiState.Success,
    onFaithClick: (FollowableTopic) -> Unit,
) {
    faithsUiState.userFaiths.forEach {
        item(
            key = it.hashCode()
        ) {
            FaithCard(
                faith = it,
                onFaithClick = onFaithClick,
                modifier = Modifier)
        }
    }
}
