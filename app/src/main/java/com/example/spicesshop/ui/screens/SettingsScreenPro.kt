package com.example.spicesshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spicesshop.data.SettingsStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenPro(settingsStore: SettingsStore) {
    val state by settingsStore.state.collectAsState()
    val scope = rememberCoroutineScope()

    var langOpen by remember { mutableStateOf(false) }
    var curOpen by remember { mutableStateOf(false) }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Settings") }) }) { pad ->
        Column(
            Modifier.padding(pad).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingSwitch("Dark Mode", state.darkMode) {
                scope.launch { settingsStore.setDark(it) }
            }
            SettingSwitch("Notifications", state.notifications) {
                scope.launch { settingsStore.setNoti(it) }
            }

            SettingAction("Language", state.language) { langOpen = true }
            SettingAction("Currency", state.currency) { curOpen = true }
        }
    }

    if (langOpen) {
        ChoiceDialog(
            title = "Select Language",
            options = listOf("English", "සිංහල", "தமிழ்"),
            selected = state.language,
            onDismiss = { langOpen = false },
            onSelect = { v -> scope.launch { settingsStore.setLang(v) }; langOpen = false }
        )
    }

    if (curOpen) {
        ChoiceDialog(
            title = "Select Currency",
            options = listOf("LKR", "USD"),
            selected = state.currency,
            onDismiss = { curOpen = false },
            onSelect = { v -> scope.launch { settingsStore.setCurrency(v) }; curOpen = false }
        )
    }
}

@Composable
private fun SettingSwitch(title: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Card {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            Switch(checked = value, onCheckedChange = onChange)
        }
    }
}

@Composable
private fun SettingAction(title: String, subtitle: String, onClick: () -> Unit) {
    Card(onClick = onClick) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(title)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("Change", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun ChoiceDialog(
    title: String,
    options: List<String>,
    selected: String,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                options.forEach { opt ->
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        RadioButton(selected = opt == selected, onClick = { onSelect(opt) })
                        Text(opt)
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Done") } }
    )
}
