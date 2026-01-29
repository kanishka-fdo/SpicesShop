package com.example.spicesshop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.spicesshop.data.CartStore
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(onBack: () -> Unit) {
    val subtotal = CartStore.subtotal()
    val delivery = CartStore.delivery()
    val total = CartStore.total()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                actions = {
                    if (CartStore.items.isNotEmpty()) {
                        TextButton(onClick = { CartStore.clear() }) { Text("Clear") }
                    }
                }
            )
        },
        bottomBar = {
            Surface(shadowElevation = 10.dp) {
                Column(Modifier.padding(16.dp)) {
                    Summary("Subtotal", subtotal)
                    Summary("Delivery", delivery)
                    Divider(Modifier.padding(vertical = 10.dp))
                    Summary("Total", total, bold = true)
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = { /* TODO add checkout */ },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = CartStore.items.isNotEmpty()
                    ) { Text("Checkout") }
                }
            }
        }
    ) { pad ->
        if (CartStore.items.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Your cart is empty")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(pad),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(CartStore.items, key = { it.spice.id }) { item ->
                    Card(shape = RoundedCornerShape(16.dp)) {
                        Row(Modifier.padding(12.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(item.spice.imageUrl),
                                contentDescription = item.spice.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(76.dp).clip(RoundedCornerShape(12.dp))
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(item.spice.name, fontWeight = FontWeight.SemiBold, maxLines = 2)
                                Spacer(Modifier.height(6.dp))
                                Text("LKR ${String.format(Locale.US, "%.0f", item.spice.price)}")

                                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                    IconButton(onClick = { CartStore.update(item.spice.id, item.quantity - 1) }) {
                                        Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Minus")
                                    }
                                    Text(item.quantity.toString())
                                    IconButton(onClick = { CartStore.update(item.spice.id, item.quantity + 1) }) {
                                        Icon(Icons.Default.AddCircleOutline, contentDescription = "Plus")
                                    }
                                    Spacer(Modifier.weight(1f))
                                    IconButton(onClick = { CartStore.remove(item.spice.id) }) {
                                        Icon(Icons.Default.DeleteOutline, contentDescription = "Remove")
                                    }
                                }
                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(90.dp)) }
            }
        }
    }
}

@Composable
private fun Summary(label: String, value: Double, bold: Boolean = false) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            "LKR ${String.format(Locale.US, "%.0f", value)}",
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
    }
}
