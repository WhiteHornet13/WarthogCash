package com.warthogcash.presupuesto.domain.model

/**
 * Las 5 categorías fijas de cada Presupuesto (mes).
 *
 * Especificación técnica, sección 5.2: los nombres de categoría están
 * definidos como un enum en código, no como datos en base de datos.
 * Solo el porcentaje asignado a cada una varía por mes y se almacena.
 */
enum class TipoCategoria(val etiqueta: String) {
    GENERAL("General"),
    AHORRO("Ahorro"),
    INVERSION("Inversión"),
    OCIO("Ocio"),
    CULTURA("Cultura");

    companion object {
        /** Orden fijo en el que deben mostrarse siempre las 5 categorías. */
        val ORDEN_VISUAL: List<TipoCategoria> = listOf(GENERAL, AHORRO, INVERSION, OCIO, CULTURA)
    }
}
