package com.onelittleangel.faiths

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.onelittleangel.designsystem.component.OlaTab
import com.onelittleangel.designsystem.component.OlaTabRow
import com.onelittleangel.designsystem.theme.PaletteTokens
import com.onelittleangel.model.ResourceKind
import com.onelittleangel.model.UserFaith
import com.onelittleangel.ui.card.CardTopic
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle

private val _tabState = MutableStateFlow(
    FilterFaithTabState(
        titles = mutableListOf(
            Resources.strings.authors_faiths),
        currentIndex = 0
    )
)

val tabState: StateFlow<FilterFaithTabState> = _tabState.asStateFlow()

fun switchTab(newIndex: Int) {
    if (newIndex != tabState.value.currentIndex) {
        _tabState.update {
            it.copy(currentIndex = newIndex)
        }
    }
}

data class FilterFaithTabState(
    val titles: MutableList<StringResource>,
    val currentIndex: Int
)

@Composable
fun FaithsDialog(
    faiths: List<UserFaith>,
    //faiths: List<UserFaith>,
    onDismiss: () -> Unit,
    filterBy: (topicName: String) -> Unit,
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { onDismiss() },
        containerColor = if(PaletteTokens.LocalSystemInDarkTheme.current)
            MaterialTheme.colorScheme.onPrimary
        else
            PaletteTokens.White,
        title = {
            Text(
                text = stringResource(Resources.strings.authors_quotes),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            val tabState by tabState.collectAsStateWithLifecycle()

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OlaTabRow(selectedTabIndex = tabState.currentIndex) {
                    tabState.titles.forEachIndexed { index, title ->
                        OlaTab(
                            selected = index == tabState.currentIndex,
                            onClick = { switchTab(index) },
                            text = { androidx.compose.material.Text(
                                modifier = Modifier.testTag(stringResource(title)),
                                text = stringResource(title),
                                fontWeight = FontWeight.ExtraBold,)
                            }
                        )
                    }
                }

                when(tabState.currentIndex) {
                    0 -> {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Adaptive(minSize =  165.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalItemSpacing = 8.dp
                        ) {
                            faiths.flatMap { faith ->
                                faith.followableTopics?.filter { it.topic.kind == ResourceKind.movement } ?: listOf()
                            }.toSet().toList().forEach {
                                item {
                                    CardTopic( followableTopic =  it, onTopicClick = { filterBy(it.topic.name) })
                                }
                            }
                        }
                    }

                    /*1 -> {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Adaptive(minSize = 165.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalItemSpacing = 8.dp
                        ) {
                            books.flatMap { book ->
                                book.followableTopics?.filter { it.topic.kind == ResourceKind.movement } ?: listOf()
                            }.toSet().toList().forEach {
                                item {
                                    CardTopic( followableTopic =  it, onTopicClick = { filterBy(it.topic.name) })
                                }
                            }
                        }
                    }*/
                }
            }
        },
        confirmButton = {
            Text(
                text = "OK",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp)
                    .clickable { onDismiss() }
            )
        }
    )
}