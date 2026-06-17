package com.onelittleangel.books

import com.onelittleangel.domain.book.GetBooksUseCase
import com.onelittleangel.model.UserBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class BooksViewModel(
    getBooksUseCase: GetBooksUseCase
): ViewModel() {

    val booksUiState: StateFlow<BooksUiState> = getBooksUseCase.booksUiStateStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = BooksUiState.Loading
        )
}

private fun GetBooksUseCase.booksUiStateStream(): Flow<BooksUiState> {
    // Observe the followed authors, as they could change over time.
    return this().map { BooksUiState.Success(userBooks = it) }
}

sealed interface BooksUiState {
    data class Success(val userBooks: List<UserBook>) : BooksUiState
    object Loading : BooksUiState
}