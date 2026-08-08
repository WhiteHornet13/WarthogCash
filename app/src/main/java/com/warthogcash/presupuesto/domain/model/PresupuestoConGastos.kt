package com.warthogcash.presupuesto.domain.model

/** Representación "en bruto" de un mes completo, con la lista real de gastos
 *  de cada categoría (no solo el total agregado que expone [Presupuesto]/
 *  [Categoria]). Se usa exclusivamente para exportar (backup JSON y CSV). */
data class PresupuestoConGastos(
    val mes: Int,
    val anio: Int,
    val dineroDisponible: Double,
    val estado: EstadoPresupuesto,
    val esActual: Boolean,
    val categorias: List<CategoriaConGastos>
)

data class CategoriaConGastos(
    val tipo: TipoCategoria,
    val porcentaje: Double,
    val gastos: List<Gasto>
)