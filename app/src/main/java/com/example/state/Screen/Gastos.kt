package com.example.state.Screen

class Gastos(var concepto: String,var monto : Double,var categoria : String) {
    override fun toString(): String {
        return "$concepto ($$monto)"
    }
}