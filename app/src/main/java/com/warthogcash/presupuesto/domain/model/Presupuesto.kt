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

    /** Suma del restante de cada categoría, sin dejar que una categoría que
     *  ha superado su límite (restante negativo) reste dos veces el mismo
     *  dinero del total: ese exceso ya se descuenta una vez de Ahorro
     *  mediante el gasto de cobertura automática (ver
     *  PresupuestoRepositoryImpl.cubrirExcesoConAhorroSiProcede). Sin este
     *  coerceAtLeast, el mismo dinero se restaba tanto en la categoría que
     *  originó el exceso (restante negativo) como en Ahorro (gasto de
     *  cobertura ya reflejado en su propio restante). */
    val totalRestante: Double
        get() = categorias.sumOf { it.restante.coerceAtLeast(0.0) }

    /** Dinero que ha quedado ahorrado en la categoría Ahorro (su "restante"
     *  real). Se muestra en el header de "Detalle de mes cerrado", entre
     *  Ingreso y Gastado. */
    val totalAhorrado: Double
        get() = categorias.firstOrNull { it.tipo == TipoCategoria.AHORRO }?.restante ?: 0.0

    /** Suma de traspasos recibidos de OTRO mes (dinero nuevo real), excluyendo
     *  los traspasos internos a Ahorro dentro de este mismo mes. */
    val totalIngresosDeMesesAnteriores: Double
        get() = categorias.sumOf { it.ingresosDeOtroMes }

    /** Dinero total realmente disponible este mes: el ingreso introducido por
     *  el usuario más lo recibido por traspaso de OTROS meses. No incluye
     *  traspasos internos a Ahorro (eso es el mismo dinero reubicado, no
     *  dinero nuevo). No confundir con [dineroDisponible], que es el ingreso
     *  "en bruto" tal cual se guardó al crear/editar el mes y nunca debe
     *  modificarse. */
    val totalAsignadoMes: Double
        get() = dineroDisponible + totalIngresosDeMesesAnteriores

    /** Fracción 0..1 gastada del total del mes, usada para la mini barra en "Mis meses". */
    val progresoTotal: Float
        get() = if (totalAsignadoMes <= 0.0) 0f else (totalGastado / totalAsignadoMes).toFloat()

    val estadoBarra: EstadoBarraProgreso
        get() = when {
            totalGastado >= totalAsignadoMes -> EstadoBarraProgreso.LIMITE_SUPERADO
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
