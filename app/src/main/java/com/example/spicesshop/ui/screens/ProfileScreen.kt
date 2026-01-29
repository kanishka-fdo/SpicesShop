package com.example.spicesshop.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onOrders: () -> Unit,
    onAddresses: () -> Unit,
    onPayments: () -> Unit,
    onWishlist: () -> Unit,
    onHelp: () -> Unit
) {
    var name by remember { mutableStateOf("Kanishka Perera") }
    var email by remember { mutableStateOf("kanishka@gmail.com") }
    var phone by remember { mutableStateOf("+94 77 123 4567") }

    var editOpen by remember { mutableStateOf(false) }
    var eName by remember { mutableStateOf(name) }
    var eEmail by remember { mutableStateOf(email) }
    var ePhone by remember { mutableStateOf(phone) }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("My Account", fontWeight = FontWeight.Bold) }) }) { pad ->
        Column(
            Modifier.padding(pad).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(shape = RoundedCornerShape(18.dp)) {
                Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Column(Modifier.weight(1f)) {
                        Text(name, fontWeight = FontWeight.Bold)
                        Text(email, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(phone, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = {
                        eName = name; eEmail = email; ePhone = phone
                        editOpen = true
                    }) { Icon(Icons.Default.Edit, contentDescription = "Edit") }
                }
            }

            Text("My Account", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            RowItem("My Orders", "Track and reorder", onOrders)
            RowItem("Addresses", "Delivery locations", onAddresses)
            RowItem("Payment Methods", "Cards & COD", onPayments)
            RowItem("Wishlist", "Saved items", onWishlist)

            Text("Support", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            RowItem("Help Center", "Support & FAQs", onHelp)
        }
    }

    if (editOpen) {
        AlertDialog(
            onDismissRequest = { editOpen = false },
            title = { Text("Edit Profile") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(eName, { eName = it }, label = { Text("Name") })
                    OutlinedTextField(eEmail, { eEmail = it }, label = { Text("Email") })
                    OutlinedTextField(ePhone, { ePhone = it }, label = { Text("Phone") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    name = eName.trim()
                    email = eEmail.trim()
                    phone = ePhone.trim()
                    editOpen = false
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { editOpen = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun RowItem(title: String, subtitle: String, onClick: () -> Unit) {
    Card(onClick = onClick, shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.padding(16.dp)) {
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}
