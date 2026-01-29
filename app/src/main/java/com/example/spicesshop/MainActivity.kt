package com.example.spicesshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.spicesshop.data.SettingsStore
import com.example.spicesshop.ui.AppRoot
import com.example.spicesshop.ui.theme.LankaMartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsStore = SettingsStore(applicationContext)

        setContent {
            val settings by settingsStore.state.collectAsState()

            LankaMartTheme(darkTheme = settings.darkMode) {
                Surface {
                    AppRoot(settingsStore = settingsStore)
                }
            }
        }
    }
}
