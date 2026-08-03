package com.warthogcash.presupuesto.util

import android.content.Context
import com.warthogcash.presupuesto.domain.model.TipoCategoria

class PorcentajesPredefinidos(context: Context) {
    private val prefs = context.getSharedPreferences("porcentajes_predefinidos", Context.MODE_PRIVATE)

    fun obtener(tipo: TipoCategoria): Double = prefs.getFloat(tipo.name, 0f).toDouble()

    fun guardar(valores: Map<TipoCategoria, Double>) {
        prefs.edit().apply {
            valores.forEach { (tipo, valor) -> putFloat(tipo.name, valor.toFloat()) }
        }.apply()
    }
}