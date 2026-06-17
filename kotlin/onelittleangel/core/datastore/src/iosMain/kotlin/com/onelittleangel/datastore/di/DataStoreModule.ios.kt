package com.onelittleangel.datastore.di

import androidx.datastore.core.DataMigration
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.onelittleangel.datastore.OlaPreferencesDataSource
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import okio.Path.Companion.toPath
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual val datastoreModule = module {
    singleOf(::OlaPreferencesDataSource)
    includes(datastorePreferences())
}

@OptIn(ExperimentalForeignApi::class)
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
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                (requireNotNull(documentDirectory).path + "/$USER_PREFERENCES").toPath()
            }
        )
    }
}