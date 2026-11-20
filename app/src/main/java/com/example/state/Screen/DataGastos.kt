package com.example.state.Screen

import kotlin.div
import kotlin.text.toFloat
import kotlin.times

data class DataGastos(
    val ingreso: String = "4500.00",
    val gastos: List<Gastos> = emptyList(),
    val agregar: Boolean = false,
    val nuevoGastos: Gastos = Gastos("", 0.00, ""),
    val concepto: String = "",
    val categorias: List<String> = listOf("Comida", "Transporte", "Renta", "Ocio", "Otros"),
    val expandido: Boolean = false,
    val categoria: String = "",
    val costo: String = ""

    ) {
    val costod: Double  get() = costo.toDoubleOrNull() ?: 0.00
    val monto: Double get() = ingreso.toDoubleOrNull() ?: 0.00
    val Saldo: Double get() = monto - gastado
    val slider: Float
        get() {
            if (monto > 0) return monto.toFloat() else return 1f
        }
    val gastadoSlider: Float get() = gastado.toFloat().coerceIn(0f, slider)

    val usado: Double get() = (gastado / monto) * 100
    val gastoMayor: Gastos?
        get() {
            if (gastos.isNotEmpty()) {
                var gMayor: Gastos = gastos[0]
                gastos.forEach { g ->
                    if (g.monto > gMayor.monto) {
                        gMayor = g
                    }
                }
                return gMayor
            } else {
                return null
            }
        }
    val gastado: Double
        get() {
            var gastado: Double = 0.00
            gastos.forEach { g ->
                gastado += g.monto
            }
            return gastado
        }


}
