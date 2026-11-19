package com.example.state.Screen

import kotlin.math.ceil

data class DatosUi(
    val cantidad: Int = 1,
    val monto: String = "",
    val propina: Double = 0.00,
    val rendondear: Boolean = false
) {
    val dinero: Double get() = monto.toDoubleOrNull() ?: 0.00

    val propinaMonto: Double get() = dinero * propina
    val total: Double get() = dinero + propina
    val porPersona: Double
        get()  {
            val base = total / cantidad
            return if (rendondear) {
                ceil(base)
            } else {
                base
            }
        }
    val sobrante: Double get() = if (rendondear) porPersona * cantidad - total else 0.0
}
