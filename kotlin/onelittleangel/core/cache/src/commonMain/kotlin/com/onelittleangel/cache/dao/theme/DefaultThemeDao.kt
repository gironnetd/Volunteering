package com.onelittleangel.cache.dao.theme

import androidx.compose.ui.text.intl.Locale
import com.onelittleangel.cache.model.CachedTheme
import com.onelittleangel.common.unaccent
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class DefaultThemeDao(private val realm: Realm) : ThemeDao {

    override fun themeById(idTheme: String): Flow<CachedTheme> {
        return flowOf(realm.query(CachedTheme::class, query = "idTheme == $0", idTheme).find().first())
    }

    override fun themeByName(name: String): Flow<CachedTheme> {
       return flowOf(realm.query(CachedTheme::class, query = "name == $0", name).find().first())
    }

    override fun themesByIdParent(idParent: String): Flow<List<CachedTheme>> = flow {
        realm.query(CachedTheme::class, query = "idParent == $0", idParent)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun mainThemes(): Flow<List<CachedTheme>> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedTheme::class, query = "language CONTAINS $0 AND idParentTheme == $1",
                Locale.current.language, null)
                .asFlow().collect { changes: ResultsChange<CachedTheme> ->
                    when (changes) {
                        is InitialResults -> send(
                            changes.list.copyFromRealm()
                                .onEach {  theme ->
                                    theme.quotes.removeAll(theme.quotes)
                                    theme.authors.removeAll(theme.authors)
                                    theme.books.removeAll(theme.books)
                                    //theme.themes.removeAll(theme.themes)
                                    theme.pictures.removeAll(theme.pictures)
                                }.sortedBy { it.name.unaccent() }
                        )
                        else -> {}
                    }
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allThemes(): Flow<List<CachedTheme>> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedTheme::class, query = "language CONTAINS $0", Locale.current.language)
                .asFlow().collect { changes: ResultsChange<CachedTheme> ->
                    when (changes) {
                        is InitialResults -> send(
                            changes.list.copyFromRealm()
                                .onEach {  theme ->
                                    theme.quotes.removeAll(theme.quotes)
                                    theme.authors.removeAll(theme.authors)
                                    theme.books.removeAll(theme.books)
                                    //theme.themes.removeAll(theme.themes)
                                    theme.pictures.removeAll(theme.pictures)
                                }
                                .sortedBy { it.name }
                        )
                        else -> {}
                    }
                }
        }
    }
}