package com.onelittleangel.foryou

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.onelittleangel.data.source.BookmarkRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.domain.foryou.GetForYouUseCase
import com.onelittleangel.model.Bookmark
import com.onelittleangel.model.UserBiography
import com.onelittleangel.model.UserPicture
import com.onelittleangel.model.UserQuote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ForYouViewModel(
   getForYouUseCase: GetForYouUseCase,
   private val bookmarkRepository: BookmarkRepository,
   private val olaPreferences: OlaPreferencesDataSource
) : ViewModel() {

   val forYouUiState: StateFlow<ForYouUiState> = getForYouUseCase.forYouUiStateStream(
      hasDateChanged = { hasDateChanged() }
   )
      .stateIn(
         scope = viewModelScope,
         started = SharingStarted.Lazily,
         initialValue = ForYouUiState.Loading
      )

   fun updateUserResourceBookmarked(bookmark: Bookmark, bookmarked: Boolean) {
      viewModelScope.launch {
         bookmarkRepository.updateResourceBookmarked(bookmark = bookmark, bookmarked = bookmarked)
      }
   }

   private fun hasDateChanged(): Unit {
      olaPreferences.hasDateChanged.map { hasDateChanged ->
         if(hasDateChanged) {
            olaPreferences.updateDate()
         }
      }
   }
}

private fun GetForYouUseCase.forYouUiStateStream(
   hasDateChanged: () -> Unit
): Flow<ForYouUiState> {
   return this().map {
      hasDateChanged()
      ForYouUiState.Success(
         forYou = Triple(
            first = mutableStateListOf<UserQuote>().apply { this.addAll(it.first) },
            second = mutableStateListOf<UserPicture>().apply { this.addAll(it.second) },
            third = mutableStateListOf<UserBiography>().apply { this.addAll(it.third) }))
   }
}

sealed interface ForYouUiState {
   data class Success(val forYou: Triple<SnapshotStateList<UserQuote>, SnapshotStateList<UserPicture>, SnapshotStateList<UserBiography>>) : ForYouUiState
   object Loading : ForYouUiState
}

/*
sealed interface ForYouQuotesUiState {
   data class Success(val quotes: List<UserQuote>) : ForYouQuotesUiState
   object Loading : ForYouQuotesUiState
}

private fun GetForYouPicturesUseCase.forYouPicturesUiStateStream(
   //tabState: StateFlow<ForYouTabState>
): Flow<ForYouPicturesUiState> {
   // Observe the followed authors, as they could change over time.
   return this().map {
      ForYouPicturesUiState.Success(pictures = it)
   }
}

sealed interface ForYouPicturesUiState {
   data class Success(val pictures: List<UserPicture>) : ForYouPicturesUiState
   object Loading : ForYouPicturesUiState
}

private fun GetForYouBiographiesUseCase.forYouBiographiesUiStateStream(

): Flow<ForYouBiographiesUiState> {
   // Observe the followed authors, as they could change over time.
   return this().map {
      ForYouBiographiesUiState.Success(biographies = it)
   }
}

sealed interface ForYouBiographiesUiState {
   data class Success(val biographies: List<UserBiography>) : ForYouBiographiesUiState
   object Loading : ForYouBiographiesUiState
}
*/
