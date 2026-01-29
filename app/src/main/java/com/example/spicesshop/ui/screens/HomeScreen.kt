package com.example.spicesshop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.spicesshop.data.SpiceRepository
import com.example.spicesshop.model.Spice
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenDetail: (Int) -> Unit,
    onOpenCart: () -> Unit,
    onOpenMap: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("All") }

    val filtered = SpiceRepository.items
        .filter { category == "All" || it.category == category }
        .filter { query.isBlank() || it.name.contains(query, true) || it.category.contains(query, true) }

    Column(Modifier.fillMaxSize()) {

        // Top header
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("LankaMart", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("Fast delivery • LKR • Sri Lanka", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = onOpenMap) { Icon(Icons.Default.LocalShipping, contentDescription = "Map") }
            IconButton(onClick = onOpenCart) { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") }
        }

        // Search
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search products, brands, categories…") },
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        // Hero deals (simple banner)
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Flash Deals 🔥", fontWeight = FontWeight.Bold)
                    Text("Up to 40% off selected items today", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                AssistChip(onClick = { category = "All" }, label = { Text("Shop now") })
            }
        }

        Spacer(Modifier.height(12.dp))

        // Categories
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(SpiceRepository.categories.size) { idx ->
                val c = SpiceRepository.categories[idx]
                FilterChip(
                    selected = category == c,
                    onClick = { category = c },
                    label = { Text(c) }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Product grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filtered, key = { it.id }) { p ->
                ProductCard(p, onClick = { onOpenDetail(p.id) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductCard(p: Spice, onClick: () -> Unit) {
    val op = p.originalPrice
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(Modifier.height(140.dp).fillMaxWidth()) {
                Image(
                    painter = rememberAsyncImagePainter(p.imageUrl),
                    contentDescription = p.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        "LKR ${String.format(Locale.US, "%.0f", p.price)}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column(Modifier.padding(12.dp)) {
                Text(p.name, fontWeight = FontWeight.SemiBold, maxLines = 2)
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("${p.rating}", style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.weight(1f))
                    Text(p.unitLabel, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                if (op != null && op > p.price) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "LKR ${String.format(Locale.US, "%.0f", op)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                if (p.freeDelivery) {
                    Spacer(Modifier.height(6.dp))
                    Text("🚚 Free Delivery", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
                }

                if (p.isRestricted) {
                    Spacer(Modifier.height(6.dp))
                    Text("⚠ Restricted", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
