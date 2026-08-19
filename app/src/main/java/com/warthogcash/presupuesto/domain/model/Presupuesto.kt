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
    /** Suma del gasto real de cada categoría. Se resta [Categoria.coberturaRecibida]
     *  porque ese importe ya se contó una vez como gasto real en la categoría
     *  que originó el exceso, y otra vez como "gastado" en Ahorro (la cobertura
     *  automática que lo cubre); sin restarlo aquí, el "Gastado" total del mes
     *  queda inflado exactamente por ese importe duplicado. */
    val totalGastado: Double
        get() = categorias.sumOf { it.gastado } - categorias.sumOf { it.coberturaRecibida }

    /** Suma de todos los traspasos recibidos por las categorías de este mes
     *  (de otra categoría del mismo mes hacia Ahorro, o del mes anterior). */
    val totalIngresosTraspasados: Double
        get() = categorias.sumOf { it.ingresosTraspasados }

    /** Suma del restante real de cada categoría, sin ocultar negativos: un
     *  sobregasto real (p. ej. Ahorro gastado por encima de lo asignado,
     *  ya que Ahorro nunca se cubre a sí misma) debe reflejarse tal cual.
     *  Se suma aparte [Categoria.coberturaRecibida] porque ese importe ya
     *  está contado una vez como gasto real en la categoría que originó el
     *  exceso (restante negativo) y otra vez como "gastado" en Ahorro (la
     *  cobertura automática); sin compensarlo se restaría dos veces. */
    val totalRestante: Double
        get() = categorias.sumOf { it.restante } + categorias.sumOf { it.coberturaRecibida }

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
