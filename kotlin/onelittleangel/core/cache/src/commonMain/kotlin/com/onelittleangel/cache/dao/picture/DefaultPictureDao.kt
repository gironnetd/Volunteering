package com.onelittleangel.cache.dao.picture

import androidx.compose.ui.text.intl.Locale
import com.onelittleangel.cache.model.CachedPicture
import io.realm.kotlin.Realm
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

class DefaultPictureDao(private val realm: Realm) : PictureDao {

    override fun pictureById(idPicture: String): Flow<CachedPicture> {
        return flowOf(realm.query(CachedPicture::class, query = "idPicture == $0", idPicture).find().first())
    }

    override fun picturesByIds(ids: List<String>): Flow<List<CachedPicture>> {
       return flowOf(realm.query(CachedPicture::class).find().toList().filter { ids.contains(it.idPicture) })
    }

    override fun picturesByIdAuthor(idAuthor: String): Flow<List<CachedPicture>> = flow {
        realm.query(CachedPicture::class, query = "idAuthor == $0", idAuthor)
    }

    override fun picturesByIdBook(idBook: String): Flow<List<CachedPicture>> = flow {
        realm.query(CachedPicture::class, query = "idBook == $0", idBook)
    }

    override fun picturesByIdBiography(idBiography: String): Flow<List<CachedPicture>> = flow {
        realm.query(CachedPicture::class, query = "idBiography == $0", idBiography)
    }

    override fun picturesByIdTheme(idTheme: String): Flow<List<CachedPicture>> = flow {
        realm.query(CachedPicture::class, query = "idTheme == $0", idTheme)
    }

    override fun picturesByNameSmall(nameSmall: String): Flow<List<CachedPicture>> = flow {
        realm.query(CachedPicture::class, query = "nameSmall == $0", nameSmall)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun allPictures(): Flow<List<CachedPicture>> = channelFlow {
        launch((Dispatchers.IO.limitedParallelism(60))) {
            realm.query(CachedPicture::class, query = "topics.kind != $0 AND topics.language CONTAINS $1","century", Locale.current.language)
                .asFlow().collect { changes: ResultsChange<CachedPicture> ->
                    when (changes) {
                        is InitialResults -> send(changes.list.shuffled().subList(0, 1))
                        else -> {}
                    }
                }
        }
    }
}