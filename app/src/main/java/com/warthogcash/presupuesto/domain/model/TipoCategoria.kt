package com.warthogcash.presupuesto.domain.model
import com.warthogcash.presupuesto.R

/**
 * Las 5 categorías fijas de cada Presupuesto (mes).
 *
 * Especificación técnica, sección 5.2: los nombres de categoría están
 * definidos como un enum en código, no como datos en base de datos.
 * Solo el porcentaje asignado a cada una varía por mes y se almacena.
 */
enum class TipoCategoria(val etiqueta: String, val colorResId: Int) {
    GENERAL("General", R.color.verde_principal),
    AHORRO("Ahorro", R.color.verde_ahorro),
    INVERSION("Inversión", R.color.acento_ambar),
    OCIO("Ocio", R.color.azul_ocio),
    CULTURA("Cultura", R.color.naranja_cultura);

    companion object {
        /** Orden fijo en el que deben mostrarse siempre las 5 categorías. */
        val ORDEN_VISUAL: List<TipoCategoria> = listOf(GENERAL, AHORRO, INVERSION, OCIO, CULTURA)
    }
}
