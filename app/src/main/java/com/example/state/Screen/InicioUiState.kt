package com.example.state.Screen

import androidx.compose.runtime.saveable.rememberSaveable

data class InicioUiState(
    val  productos : List<Producto> = listOf(
            Producto("Tortillas 1kg", 24.00, 0),
            Producto("Leche 1L", 27.50, 0),
            Producto("Huevo 12 pz", 52.00, 0),
            Producto("Frijoles 1kg", 38.00, 0)
    ),
    val mostrarConfirmacion: Boolean = false,
    val MAX: Int = 10
) {
    val totalArticulos: Int get() = productos.sumOf { it.cantidad }
    val subtotal: Double get() = productos.sumOf { it.cantidad * it.precio }
    val descuento: Double get() = if (subtotal >= 200) subtotal * 0.10 else 0.0
    val envioGratis: Boolean get() = subtotal >= 150
    val envio: Double get() = if (subtotal == 0.0 || envioGratis) {
        0.0
    } else {
        25.0
    }
    val total: Double get() = subtotal - descuento + envio
}
