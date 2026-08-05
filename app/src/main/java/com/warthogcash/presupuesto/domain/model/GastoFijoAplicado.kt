package com.warthogcash.presupuesto.domain.model

/**
 * Un gasto fijo ya confirmado por el usuario para aplicarse a un mes
 * concreto. [coste] puede haberse ajustado solo para ese mes, sin que
 * eso modifique el gasto fijo original (ver "Seleccionar gastos fijos").
 */
data class GastoFijoAplicado(
    val tipo: TipoCategoria,
    val coste: Double,
    val comentario: String?
)