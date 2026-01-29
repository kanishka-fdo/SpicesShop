package com.example.spicesshop.model

data class Spice(
    val id: Int,
    val name: String,
    val category: String,
    val imageUrl: String,
    val price: Double,
    val originalPrice: Double? = null,
    val rating: Double = 4.5,
    val unitLabel: String = "1 Unit",
    val freeDelivery: Boolean = false,

    // ✅ Restricted items
    val isRestricted: Boolean = false,
    val requiresAge18: Boolean = false,
    val requiresNic: Boolean = false,
    val restrictionNote: String = ""
)
