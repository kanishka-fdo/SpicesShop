package com.example.spicesshop.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private val Context.ds: DataStore<Preferences> by preferencesDataStore("lankamart_settings")

data class SettingsState(
    val darkMode: Boolean = false,
    val notifications: Boolean = true,
    val language: String = "English",
    val currency: String = "LKR"
)

class SettingsStore(private val context: Context) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private object K {
        val DARK = booleanPreferencesKey("dark")
        val NOTI = booleanPreferencesKey("notifications")
        val LANG = stringPreferencesKey("language")
        val CURR = stringPreferencesKey("currency")
    }

    val state = context.ds.data
        .map { p ->
            SettingsState(
                darkMode = p[K.DARK] ?: false,
                notifications = p[K.NOTI] ?: true,
                language = p[K.LANG] ?: "English",
                currency = p[K.CURR] ?: "LKR"
            )
        }
        .stateIn(scope, SharingStarted.Eagerly, SettingsState())

    suspend fun setDark(v: Boolean) = context.ds.edit { it[K.DARK] = v }
    suspend fun setNoti(v: Boolean) = context.ds.edit { it[K.NOTI] = v }
    suspend fun setLang(v: String) = context.ds.edit { it[K.LANG] = v }
    suspend fun setCurrency(v: String) = context.ds.edit { it[K.CURR] = v }
}
