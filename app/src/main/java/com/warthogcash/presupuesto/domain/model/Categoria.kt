package com.warthogcash.presupuesto.domain.model

data class Categoria(
    val id: Long,
    val presupuestoId: Long,
    val tipo: TipoCategoria,
    val porcentaje: Double,
    val montoAsignadoBase: Double,
    val ingresosTraspasados: Double = 0.0,
    /** Gasto real + traspasado al mes siguiente (cuenta como gasto a
     *  efectos de cálculo, ver sumarPorCategoria en GastoDao). */
    val gastado: Double,
    /** Sobrante que esta categoría traspasó a Ahorro (mismo mes). */
    val traspasadoAhorro: Double = 0.0,
    /** Sobrante que esta categoría traspasó al mes siguiente. Ya está
     *  incluido dentro de [gastado]; se guarda aparte solo para decidir
     *  el estado visual a mostrar. */
    val traspasadoOtroMes: Double = 0.0
) {
    val montoAsignado: Double
        get() = montoAsignadoBase + ingresosTraspasados

    val restante: Double
        get() = montoAsignado - gastado - traspasadoAhorro

    val progreso: Float
        get() = if (montoAsignado <= 0.0) 0f else ((gastado + traspasadoAhorro) / montoAsignado).toFloat()

    val estado: EstadoBarraProgreso
        get() = when {
            traspasadoOtroMes > 0.0 -> EstadoBarraProgreso.RESTANTE_TRASPASADO
            traspasadoAhorro > 0.0 -> EstadoBarraProgreso.RESTANTE_AHORRADO
            gastado >= montoAsignado -> EstadoBarraProgreso.LIMITE_SUPERADO
            progreso >= 0.85f -> EstadoBarraProgreso.CERCA_DEL_LIMITE
            else -> EstadoBarraProgreso.NORMAL
        }
}

enum class EstadoBarraProgreso {
    NORMAL,
    CERCA_DEL_LIMITE,
    LIMITE_SUPERADO,
    RESTANTE_TRASPASADO,
    RESTANTE_AHORRADO
}