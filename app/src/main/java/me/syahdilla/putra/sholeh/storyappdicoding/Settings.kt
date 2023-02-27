package me.syahdilla.putra.sholeh.storyappdicoding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val SHARED_PREFERENCE_NAME = "settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SHARED_PREFERENCE_NAME)

val PREF_USER_SESSION = stringPreferencesKey("user_session")