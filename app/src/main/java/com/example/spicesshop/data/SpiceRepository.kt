package com.example.spicesshop.data

import com.example.spicesshop.model.Spice

object SpiceRepository {

    val categories = listOf(
        "All", "Groceries", "Tea & Spices", "Fashion", "Electronics", "Health"
    )

    val items = listOf(
        Spice(
            id = 1,
            name = "Samsung Galaxy A15 (4GB/128GB)",
            category = "Electronics",
            imageUrl = "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=1200",
            price = 58900.0,
            originalPrice = 69990.0,
            rating = 4.5,
            unitLabel = "1 Unit",
            freeDelivery = true
        ),
        Spice(
            id = 2,
            name = "Ceylon Black Tea 500g",
            category = "Tea & Spices",
            imageUrl = "https://images.unsplash.com/photo-1518976024611-488a94d3f3b2?w=1200",
            price = 1650.0,
            originalPrice = 1990.0,
            rating = 4.7,
            unitLabel = "500g",
            freeDelivery = true
        ),
        Spice(
            id = 3,
            name = "Aroma Rice 5kg",
            category = "Groceries",
            imageUrl = "https://images.unsplash.com/photo-1604908812795-999d8a4b8f00?w=1200",
            price = 3890.0,
            rating = 4.4,
            unitLabel = "5kg"
        ),
        Spice(
            id = 4,
            name = "Ladies Batik Dress",
            category = "Fashion",
            imageUrl = "https://images.unsplash.com/photo-1520975958225-9bcd1c1c4f48?w=1200",
            price = 4900.0,
            originalPrice = 6900.0,
            rating = 4.6,
            unitLabel = "Size M"
        ),
        Spice(
            id = 5,
            name = "Cough Syrup (Restricted Demo)",
            category = "Health",
            imageUrl = "https://images.unsplash.com/photo-1580281658628-9f5d7a33e5c3?w=1200",
            price = 1450.0,
            rating = 4.2,
            unitLabel = "100ml",
            isRestricted = true,
            requiresAge18 = true,
            requiresNic = true,
            restrictionNote = "Requires 18+ confirmation and NIC for purchase (demo)."
        )
    )

    fun getById(id: Int): Spice? = items.firstOrNull { it.id == id }
}
