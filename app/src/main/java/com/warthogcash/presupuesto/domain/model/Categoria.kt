package com.warthogcash.presupuesto.domain.model

/**
 * Una de las 5 categorías fijas dentro de un Presupuesto (mes).
 *
 * Especificación técnica, sección 5.3: los importes "gastado" y "restante"
 * no se almacenan como campos propios; se calculan a partir de la suma de
 * los Gasto asociados, para evitar datos duplicados o desincronizados.
 * Por eso [gastado] llega ya calculado desde el repositorio y no desde Room.
 */
data class Categoria(
    val id: Long,
    val presupuestoId: Long,
    val tipo: TipoCategoria,
    val porcentaje: Double,
    val montoAsignado: Double,
    val gastado: Double
) {
    val restante: Double
        get() = montoAsignado - gastado

    /** Progreso 0..1 (puede superar 1 si el gasto excede lo asignado). */
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
