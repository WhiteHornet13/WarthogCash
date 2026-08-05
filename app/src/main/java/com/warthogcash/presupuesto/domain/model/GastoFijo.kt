package com.warthogcash.presupuesto.domain.model

/**
 * Gasto fijo definido por el usuario: plantilla reutilizable mes a mes
 * (coste de referencia, categoría, comentario). Se gestiona desde la
 * pantalla "Gastos fijos" (Ajustes) y se ofrece al crear cada mes nuevo.
 */
data class GastoFijo(
    val id: Long,
    val coste: Double,
    val tipo: TipoCategoria,
    val comentario: String?
)