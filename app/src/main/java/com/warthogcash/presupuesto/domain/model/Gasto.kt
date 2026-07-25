package com.warthogcash.presupuesto.domain.model

/**
 * Un gasto individual, ligado a una Categoria (especificación técnica, sección 5.1).
 * Modelo de dominio: sin dependencia de Room, consumido por la UI.
 */
data class Gasto(
    val id: Long,
    val categoriaId: Long,
    val importe: Double,
    val descripcion: String?,
    /** Instante de creación en millis epoch, usado para ordenar el historial. */
    val fecha: Long
)
