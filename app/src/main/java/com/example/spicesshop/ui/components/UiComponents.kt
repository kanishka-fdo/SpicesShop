package com.example.spicesshop.ui.components

import java.util.Locale
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.spicesshop.model.Spice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(
    title: String,
    query: String,
    onQueryChange: (String) -> Unit,
    onMapClick: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            IconButton(onClick = onMapClick) { Icon(Icons.Default.LocalShipping, contentDescription = "Map") }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Search products, brands, categories...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(spice: Spice, onClick: () -> Unit) {
    val op = spice.originalPrice
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box(Modifier.height(140.dp).fillMaxWidth()) {
                Image(
                    painter = rememberAsyncImagePainter(spice.imageUrl),
                    contentDescription = spice.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    modifier = Modifier.align(Alignment.TopStart).padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        "LKR ${String.format(Locale.US, "%.0f", spice.price)}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column(Modifier.padding(12.dp)) {
                Text(spice.name, fontWeight = FontWeight.SemiBold, maxLines = 2)
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⭐ ${spice.rating}")
                    Spacer(Modifier.weight(1f))
                    Text(spice.unitLabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(Modifier.height(6.dp))
                if (op != null && op > spice.price) {
                    Text(
                        "LKR ${String.format(Locale.US, "%.0f", op)}",
                        textDecoration = TextDecoration.LineThrough,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                if (spice.freeDelivery) {
                    Text("🚚 Free Delivery", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
                }
                if (spice.isRestricted) {
                    Text("⚠ Restricted item", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
