package com.warthogcash.presupuesto.util

import android.content.Context

/**
 * Umbrales (%) que determinan el color de las barras de progreso de
 * categoría y de mes. Editables desde Ajustes; persistidos en
 * SharedPreferences. Los valores por defecto son los originales.
 */
class UmbralesColores(context: Context) {
    private val prefs = context.getSharedPreferences("umbrales_colores", Context.MODE_PRIVATE)

    companion object {
        const val DEFECTO_CATEGORIA_MEDIO = 50.0
        const val DEFECTO_CATEGORIA_ALTO = 85.0
        const val DEFECTO_MES_MEDIO = 60.0
        const val DEFECTO_MES_ALTO = 100.0
    }

    var umbralCategoriaMedio: Double
        get() = prefs.getFloat("categoria_medio", DEFECTO_CATEGORIA_MEDIO.toFloat()).toDouble()
        set(valor) = prefs.edit().putFloat("categoria_medio", valor.toFloat()).apply()

    var umbralCategoriaAlto: Double
        get() = prefs.getFloat("categoria_alto", DEFECTO_CATEGORIA_ALTO.toFloat()).toDouble()
        set(valor) = prefs.edit().putFloat("categoria_alto", valor.toFloat()).apply()

    var umbralMesMedio: Double
        get() = prefs.getFloat("mes_medio", DEFECTO_MES_MEDIO.toFloat()).toDouble()
        set(valor) = prefs.edit().putFloat("mes_medio", valor.toFloat()).apply()

    var umbralMesAlto: Double
        get() = prefs.getFloat("mes_alto", DEFECTO_MES_ALTO.toFloat()).toDouble()
        set(valor) = prefs.edit().putFloat("mes_alto", valor.toFloat()).apply()

    fun restaurarPorDefecto() {
        prefs.edit()
            .putFloat("categoria_medio", DEFECTO_CATEGORIA_MEDIO.toFloat())
            .putFloat("categoria_alto", DEFECTO_CATEGORIA_ALTO.toFloat())
            .putFloat("mes_medio", DEFECTO_MES_MEDIO.toFloat())
            .putFloat("mes_alto", DEFECTO_MES_ALTO.toFloat())
            .apply()
    }
}