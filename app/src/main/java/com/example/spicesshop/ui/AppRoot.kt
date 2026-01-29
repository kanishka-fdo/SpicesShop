package com.example.spicesshop.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.spicesshop.data.SettingsStore
import com.example.spicesshop.ui.screens.*

private sealed class Route(val r: String) {
    data object Home : Route("home")
    data object Profile : Route("profile")
    data object Settings : Route("settings")
    data object Cart : Route("cart")
    data object Map : Route("map")
    data object Orders : Route("orders")
    data object Wishlist : Route("wishlist")
    data object Addresses : Route("addresses")
    data object Payments : Route("payments")
    data object Help : Route("help")
    data object Detail : Route("detail/{id}") { fun go(id: Int) = "detail/$id" }
}

@Composable
fun AppRoot(settingsStore: SettingsStore) {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    val bottom = listOf(
        Triple(Route.Home, "Home", Icons.Default.Home),
        Triple(Route.Profile, "Profile", Icons.Default.Person),
        Triple(Route.Settings, "Settings", Icons.Default.Settings),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottom.forEach { (route, label, icon) ->
                    NavigationBarItem(
                        selected = current == route.r,
                        onClick = {
                            nav.navigate(route.r) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { pad ->
        NavHost(navController = nav, startDestination = Route.Home.r) {
            composable(Route.Home.r) {
                HomeScreen(
                    onOpenDetail = { nav.navigate(Route.Detail.go(it)) },
                    onOpenCart = { nav.navigate(Route.Cart.r) },
                    onOpenMap = { nav.navigate(Route.Map.r) }
                )
            }

            composable(Route.Profile.r) {
                ProfileScreen(
                    onOrders = { nav.navigate(Route.Orders.r) },
                    onAddresses = { nav.navigate(Route.Addresses.r) },
                    onPayments = { nav.navigate(Route.Payments.r) },
                    onWishlist = { nav.navigate(Route.Wishlist.r) },
                    onHelp = { nav.navigate(Route.Help.r) }
                )
            }

            composable(Route.Settings.r) {
                SettingsScreenPro(settingsStore = settingsStore)
            }

            composable(Route.Cart.r) {
                CartScreen(onBack = { nav.popBackStack() })
            }

            composable(Route.Map.r) {
                MapScreen(onBack = { nav.popBackStack() })
            }

            composable(Route.Orders.r) { SimplePage(title = "My Orders", onBack = { nav.popBackStack() }) }
            composable(Route.Wishlist.r) { SimplePage(title = "Wishlist", onBack = { nav.popBackStack() }) }
            composable(Route.Addresses.r) { SimplePage(title = "Addresses", onBack = { nav.popBackStack() }) }
            composable(Route.Payments.r) { SimplePage(title = "Payment Methods", onBack = { nav.popBackStack() }) }
            composable(Route.Help.r) { SimplePage(title = "Help Center", onBack = { nav.popBackStack() }) }

            composable(Route.Detail.r) { entry ->
                val id = entry.arguments?.getString("id")?.toIntOrNull() ?: 0
                DetailScreen(
                    spiceId = id,
                    onBack = { nav.popBackStack() },
                    onOpenCart = { nav.navigate(Route.Cart.r) }
                )
            }
        }
    }
}
