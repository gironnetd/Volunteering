package com.onelittleangel.domain.foryou

import com.onelittleangel.data.source.QuoteRepository
import com.onelittleangel.data.source.UserDataRepository
import com.onelittleangel.datastore.OlaPreferencesDataSource
import com.onelittleangel.model.UserQuote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map

class GetForYouQuotesUseCase(
    private val quoteRepository: QuoteRepository,
    private val userDataRepository: UserDataRepository,
    private val olaPreferences: OlaPreferencesDataSource
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<UserQuote>> {
        return olaPreferences.hasDateChanged.map { hasDateChanged ->
            combine(
                olaPreferences.userDataStream,
                (if(hasDateChanged) {
                    quoteRepository.allQuotes().map {
                        it//.filter { it.language.subSequence(0, 2) == Locale.current.language }
                            //.shuffled().subList(0, 3)
                    }
                } else {
                    olaPreferences.dailyQuoteIds.map { ids ->
                        quoteRepository.quotesByIds(ids.toList())
                    }.flattenConcat()
                })
            ) { userData, quotes ->
                if(hasDateChanged) {
                    olaPreferences.setDailyQuoteIds(quotes.map { it.idQuote }.toSet())
                }

                quotes.map { UserQuote(quote = it, userData = userData) }
            }.map { it }
        }.flattenConcat()
    }
}