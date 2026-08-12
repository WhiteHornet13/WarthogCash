package com.warthogcash.presupuesto.domain.model

/**
 * Un mes concreto de presupuesto: mes, año, dinero disponible, estado
 * (abierto/cerrado) y si es el mes "actual" (especificación técnica, 5.1).
 * Modelo de dominio: sin dependencia de Room, consumido por la UI.
 */
data class Presupuesto(
    val id: Long,
    val mes: Int,
    val anio: Int,
    val dineroDisponible: Double,
    val estado: EstadoPresupuesto,
    val esActual: Boolean,
    val categorias: List<Categoria> = emptyList()
) {
    val totalGastado: Double
        get() = categorias.sumOf { it.gastado }

    /** Suma de todos los traspasos recibidos por las categorías de este mes
     *  (de otra categoría del mismo mes hacia Ahorro, o del mes anterior). */
    val totalIngresosTraspasados: Double
        get() = categorias.sumOf { it.ingresosTraspasados }

    val totalRestante: Double
        get() = categorias.sumOf { it.restante }

    /** Fracción 0..1 gastada del total del mes, usada para la mini barra en "Mis meses". */
    val progresoTotal: Float
        get() = if (dineroDisponible <= 0.0) 0f else (totalGastado / dineroDisponible).toFloat()

    val estadoBarra: EstadoBarraProgreso
        get() = when {
            totalGastado >= dineroDisponible -> EstadoBarraProgreso.LIMITE_SUPERADO
            progresoTotal >= 0.85f -> EstadoBarraProgreso.CERCA_DEL_LIMITE
            else -> EstadoBarraProgreso.NORMAL
        }
    val nombreMesAnio: String
        get() = "${NOMBRES_MES[mes - 1]} $anio"

    companion object {
        val NOMBRES_MES = listOf(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        )
    }
}
