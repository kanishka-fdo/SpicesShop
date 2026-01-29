package com.example.spicesshop.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.spicesshop.model.Spice

data class CartItem(val spice: Spice, var quantity: Int)

object CartStore {
    private val _items: SnapshotStateList<CartItem> = mutableStateListOf()
    val items: SnapshotStateList<CartItem> get() = _items

    fun add(spice: Spice, qty: Int = 1) {
        if (qty <= 0) return
        val existing = _items.firstOrNull { it.spice.id == spice.id }
        if (existing != null) existing.quantity += qty else _items.add(CartItem(spice, qty))
    }

    fun update(id: Int, qty: Int) {
        val item = _items.firstOrNull { it.spice.id == id } ?: return
        if (qty <= 0) _items.remove(item) else item.quantity = qty
    }

    fun remove(id: Int) = _items.removeAll { it.spice.id == id }
    fun clear() = _items.clear()

    fun subtotal(): Double = _items.sumOf { it.spice.price * it.quantity }
    fun delivery(): Double = if (_items.isEmpty() || _items.any { it.spice.freeDelivery }) 0.0 else 390.0
    fun total(): Double = subtotal() + delivery()
}
