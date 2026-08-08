package com.warthogcash.presupuesto.domain.model

/** Series de 12 posiciones (índice 0 = Enero) para un año concreto.
 *  Se usa en las gráficas de tipo 3 y 4 ("Gráficas", puntos acordados). */
data class ResumenMensual(
    val gasto: FloatArray,
    val ahorro: FloatArray,
    val ingreso: FloatArray
)

/** Totales anuales, uno por año solicitado. Se usa en la gráfica de tipo 5. */
data class ResumenAnual(
    val gasto: Map<Int, Float>,
    val ahorro: Map<Int, Float>,
    val ingreso: Map<Int, Float>
)