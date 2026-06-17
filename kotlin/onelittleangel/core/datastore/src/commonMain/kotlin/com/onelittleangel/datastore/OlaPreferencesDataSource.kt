package com.onelittleangel.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.onelittleangel.model.DarkThemeConfig
import com.onelittleangel.model.ThemeBrand
import com.onelittleangel.model.UserData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class OlaPreferencesDataSource(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val LAST_DATE_UPDATED = stringPreferencesKey("date")
        val DAILY_QUOTE_IDS = stringSetPreferencesKey("daily_quote_ids")
        val DAILY_PICTURE_IDS = stringSetPreferencesKey("daily_picture_ids")
        val DAILY_BIOGRAPHY_IDS = stringSetPreferencesKey("daily_biography_ids")
        val THEME_BRAND = stringPreferencesKey("theme_brand")
        val DARK_THEME_CONFIG = stringPreferencesKey("dark_theme_config")
        val BOOKMARKED_RESOURCES_IDS = stringSetPreferencesKey("bookmarked_resources_ids")
        val BOOKMARK_CHANGE_VERSIONS = intPreferencesKey("bookmark_change_versions")
        val UID_USER = stringPreferencesKey("uid_user")
    }

    val userDataStream = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                //Logger.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserData(
                themeBrand = ThemeBrand.valueOf(preferences[PreferencesKeys.THEME_BRAND] ?: ThemeBrand.primary.name),
                darkThemeConfig = DarkThemeConfig.valueOf(preferences[PreferencesKeys.DARK_THEME_CONFIG] ?: DarkThemeConfig.systemDefault.name),
                bookmarkedResources = preferences[PreferencesKeys.BOOKMARKED_RESOURCES_IDS]?.toSet() ?: emptySet(),
                uidUser = preferences[PreferencesKeys.UID_USER]
            )
    }

    var hasDateChanged = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                //Logger.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            if(preferences[PreferencesKeys.LAST_DATE_UPDATED] == null) {
                updateDate()
                true
            } else {
                if(preferences[PreferencesKeys.LAST_DATE_UPDATED] != Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()) {
                    updateDate()
                    true
                } else {
                    false
                }
            }
        }

    suspend fun updateDate() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_DATE_UPDATED] = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
        }
    }

    val dailyQuoteIds = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DAILY_QUOTE_IDS]?.toSet() ?: emptySet()
    }

    val dailyPictureIds = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DAILY_PICTURE_IDS]?.toSet() ?: emptySet()
    }

    val dailyBiographyIds = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DAILY_BIOGRAPHY_IDS]?.toSet() ?: emptySet()
    }

    suspend fun setDailyQuoteIds(ids: Set<String>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DAILY_QUOTE_IDS] = ids
        }
    }

    suspend fun setDailyPictureIds(ids: Set<String>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DAILY_PICTURE_IDS] = ids
        }
    }

    suspend fun setDailyBiographyIds(ids: Set<String>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DAILY_BIOGRAPHY_IDS] = ids
        }
    }

    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_BRAND] = themeBrand.name
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME_CONFIG] = darkThemeConfig.name
        }
    }

    suspend fun setResourceBookmarked(resourceId: String, bookmarked: Boolean) {
        dataStore.edit { preferences ->
            val bookmarkedResources = preferences[PreferencesKeys.BOOKMARKED_RESOURCES_IDS]?.toMutableSet() ?: mutableSetOf()

            if (bookmarked) {
                if (!bookmarkedResources.contains(resourceId)) {
                    bookmarkedResources.add(resourceId)
                }
            } else {
                bookmarkedResources.remove(resourceId)
            }
            preferences[PreferencesKeys.BOOKMARKED_RESOURCES_IDS] = bookmarkedResources
        }
    }

    suspend fun getChangeBookmarkVersions() = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.BOOKMARK_CHANGE_VERSIONS]?.let {
            ChangeBookmarkVersions(bookmarkVersion = it)
        } ?: ChangeBookmarkVersions()
    }

    suspend fun updateChangeBookmarkVersion(update: ChangeBookmarkVersions.() -> ChangeBookmarkVersions) {
        try {
            dataStore.edit { currentPreferences ->
                val updatedChangeBookmarkVersions = update(
                    currentPreferences[PreferencesKeys.BOOKMARK_CHANGE_VERSIONS]?.let {
                        ChangeBookmarkVersions(bookmarkVersion = it)
                    } ?: ChangeBookmarkVersions(),
                )

                currentPreferences[PreferencesKeys.BOOKMARK_CHANGE_VERSIONS] = updatedChangeBookmarkVersions.bookmarkVersion
            }
        } catch (ioException: IOException) {
            //Logger.e("NiaPreferences", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setUidUser(uidUser: String?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.UID_USER] = uidUser.orEmpty()
        }
    }
}

