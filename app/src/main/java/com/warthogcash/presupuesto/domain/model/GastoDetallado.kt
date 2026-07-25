package com.warthogcash.presupuesto.domain.model

/**
 * Un [Gasto] junto con el tipo de categoría al que pertenece. Se usa en
 * la pantalla "Historial de gastos", cuyo modo genérico muestra la
 * etiqueta de categoría en cada fila (especificación de pantalla
 * "Historial de gastos", sección 3.1 y 4.2).
 */
data class GastoDetallado(
    val gasto: Gasto,
    val categoria: TipoCategoria
)
