package com.example.spicesshop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.spicesshop.data.CartStore
import com.example.spicesshop.data.SpiceRepository
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    spiceId: Int,
    onBack: () -> Unit,
    onOpenCart: () -> Unit
) {
    val p = SpiceRepository.getById(spiceId) ?: return

    var qty by remember { mutableStateOf(1) }

    // Restricted controls
    var ageOk by remember { mutableStateOf(false) }
    var nic by remember { mutableStateOf("") }

    val restrictedOk =
        if (!p.isRestricted) true
        else {
            val a = if (p.requiresAge18) ageOk else true
            val n = if (p.requiresNic) nic.trim().length >= 9 else true
            a && n
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(p.name, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                actions = {
                    IconButton(onClick = onOpenCart) { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 8.dp) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(onClick = { if (qty > 1) qty-- }) { Text("−") }
                    Text("Qty $qty", modifier = Modifier.padding(top = 12.dp))
                    OutlinedButton(onClick = { qty++ }) { Text("+") }

                    Spacer(Modifier.weight(1f))

                    Button(
                        onClick = { CartStore.add(p, qty) },
                        enabled = restrictedOk
                    ) {
                        Text("Add • LKR ${String.format(Locale.US, "%.0f", p.price * qty)}")
                    }
                }
            }
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberAsyncImagePainter(p.imageUrl),
                contentDescription = p.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )

            Column(Modifier.padding(16.dp)) {
                Text("LKR ${String.format(Locale.US, "%.0f", p.price)}", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(6.dp))
                Text("⭐ ${p.rating} • ${p.unitLabel}")

                if (p.isRestricted) {
                    Spacer(Modifier.height(14.dp))
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("Restricted item", style = MaterialTheme.typography.titleMedium)
                            Text(
                                if (p.restrictionNote.isNotBlank()) p.restrictionNote
                                else "Extra confirmation required to purchase."
                            )

                            if (p.requiresAge18) {
                                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                    Checkbox(checked = ageOk, onCheckedChange = { ageOk = it })
                                    Text("I confirm I am 18+")
                                }
                            }

                            if (p.requiresNic) {
                                OutlinedTextField(
                                    value = nic,
                                    onValueChange = { nic = it },
                                    label = { Text("NIC number (demo)") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            if (!restrictedOk) {
                                Text(
                                    "Complete the checks to enable Add to Cart.",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
