package com.warthogcash.presupuesto.domain.model

data class Categoria(
    val id: Long,
    val presupuestoId: Long,
    val tipo: TipoCategoria,
    val porcentaje: Double,
    /** Monto asignado según el % del dinero disponible del mes, SIN traspasos. */
    val montoAsignadoBase: Double,
    /** Suma de traspasos recibidos de otras categorías/meses (nunca negativo). */
    val ingresosTraspasados: Double = 0.0,
    /** Solo gastos reales; nunca incluye traspasos. */
    val gastado: Double
) {
    /** Total disponible en la categoría: lo asignado por % + lo recibido por traspaso. */
    val montoAsignado: Double
        get() = montoAsignadoBase + ingresosTraspasados

    val restante: Double
        get() = montoAsignado - gastado

    val progreso: Float
        get() = if (montoAsignado <= 0.0) 0f else (gastado / montoAsignado).toFloat()

    val estado: EstadoBarraProgreso
        get() = when {
            gastado >= montoAsignado -> EstadoBarraProgreso.LIMITE_SUPERADO
            progreso >= 0.85f -> EstadoBarraProgreso.CERCA_DEL_LIMITE
            else -> EstadoBarraProgreso.NORMAL
        }
}

/**
 * Estados visuales de la barra de progreso de categoría.
 * Ver especificación de pantalla "Pantalla principal", sección 3.2.
 */
enum class EstadoBarraProgreso {
    NORMAL,
    CERCA_DEL_LIMITE,
    LIMITE_SUPERADO
}
