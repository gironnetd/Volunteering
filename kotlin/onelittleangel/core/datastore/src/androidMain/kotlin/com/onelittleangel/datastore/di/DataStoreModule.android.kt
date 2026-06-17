package com.onelittleangel.datastore.di

import androidx.datastore.core.DataMigration
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.onelittleangel.datastore.OlaPreferencesDataSource
import kotlinx.coroutines.CoroutineScope
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val datastoreModule = module {
    singleOf(::OlaPreferencesDataSource)
    includes(datastorePreferences())
}

actual fun datastorePreferences(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
    coroutineScope: CoroutineScope,
    migrations: List<DataMigration<Preferences>>,
) = module {
    single {
        PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = corruptionHandler,
            scope = coroutineScope,
            migrations = migrations,
            produceFile = {
                androidContext().preferencesDataStoreFile(USER_PREFERENCES).path.toPath()
            }
        )
    }
}
