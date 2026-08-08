package com.warthogcash.presupuesto.util

import com.warthogcash.presupuesto.domain.model.EstadoPresupuesto
import com.warthogcash.presupuesto.domain.model.PresupuestoConGastos
import com.warthogcash.presupuesto.domain.model.ResumenAnual
import com.warthogcash.presupuesto.domain.model.ResumenMensual
import com.warthogcash.presupuesto.domain.model.TipoCategoria

/**
 * Cálculos para las 5 gráficas de la pantalla "Gráficas". Todas las
 * funciones ignoran los meses ABIERTOS: solo se usan datos de meses
 * CERRADOS (definitivos), tal como se acordó para cada una de las 5.
 */
object EstadisticasCalculator {

    /** Años que tienen al menos un mes cerrado; para poblar los selectores. */
    fun aniosDisponibles(meses: List<PresupuestoConGastos>): List<Int> =
        meses.filter { it.estado == EstadoPresupuesto.CERRADO }
            .map { it.anio }
            .distinct()
            .sorted()

    private fun sumaGastosReales(gastos: List<com.warthogcash.presupuesto.domain.model.Gasto>): Double =
        gastos.filter { !it.esIngreso && !it.esTraspasoSalida }.sumOf { it.importe }

    /** Ahorro del mes = lo que queda en la categoría Ahorro al cerrar el mes:
     *  asignado por % + ingresos por traspaso recibidos − gastos reales
     *  registrados ahí (retiros). Es el "restante" de esa categoría. */
    private fun ahorroDelMes(mes: PresupuestoConGastos): Double {
        val categoriaAhorro = mes.categorias.firstOrNull { it.tipo == TipoCategoria.AHORRO } ?: return 0.0
        val asignadoBase = mes.dineroDisponible * (categoriaAhorro.porcentaje / 100.0)
        val ingresosRecibidos = categoriaAhorro.gastos.filter { it.esIngreso }.sumOf { it.importe }
        val gastosReales = categoriaAhorro.gastos.filter { !it.esIngreso && !it.esTraspasoSalida }.sumOf { it.importe }
        return asignadoBase + ingresosRecibidos - gastosReales
    }

    /** Gráfica 1: gasto de cada categoría, mes a mes, en un año concreto. */
    fun gastoPorCategoriaMensual(meses: List<PresupuestoConGastos>, anio: Int): Map<TipoCategoria, FloatArray> {
        val resultado = TipoCategoria.ORDEN_VISUAL.associateWith { FloatArray(12) }
        meses.filter { it.estado == EstadoPresupuesto.CERRADO && it.anio == anio }
            .forEach { mes ->
                mes.categorias.forEach { categoria ->
                    resultado[categoria.tipo]?.set(mes.mes - 1, sumaGastosReales(categoria.gastos).toFloat())
                }
            }
        return resultado
    }

    /** Gráfica 2: gasto de cada categoría en UN mes concreto, comparado entre varios años. */
    fun gastoCategoriaPorAnios(
        meses: List<PresupuestoConGastos>,
        mes: Int,
        anios: List<Int>
    ): Map<TipoCategoria, Map<Int, Float>> {
        val resultado = TipoCategoria.ORDEN_VISUAL.associateWith {
            anios.associateWith { 0f }.toMutableMap()
        }
        meses.filter { it.estado == EstadoPresupuesto.CERRADO && it.mes == mes && it.anio in anios }
            .forEach { m ->
                m.categorias.forEach { categoria ->
                    resultado[categoria.tipo]?.put(m.anio, sumaGastosReales(categoria.gastos).toFloat())
                }
            }
        return resultado
    }

    /** Gráficas 3/4: gasto, ahorro e ingreso mes a mes de UN año. */
    fun resumenMensual(meses: List<PresupuestoConGastos>, anio: Int): ResumenMensual {
        val gasto = FloatArray(12)
        val ahorro = FloatArray(12)
        val ingreso = FloatArray(12)
        meses.filter { it.estado == EstadoPresupuesto.CERRADO && it.anio == anio }
            .forEach { mes ->
                val idx = mes.mes - 1
                ingreso[idx] = mes.dineroDisponible.toFloat()
                gasto[idx] = mes.categorias.sumOf { sumaGastosReales(it.gastos) }.toFloat()
                ahorro[idx] = ahorroDelMes(mes).toFloat()
            }
        return ResumenMensual(gasto, ahorro, ingreso)
    }

    /** Gráfica 4: lo mismo que [resumenMensual], pero para varios años a la vez. */
    fun resumenMensualPorAnios(meses: List<PresupuestoConGastos>, anios: List<Int>): Map<Int, ResumenMensual> =
        anios.associateWith { resumenMensual(meses, it) }

    /** Gráfica 5: gasto, ahorro e ingreso TOTAL de cada año solicitado. */
    fun resumenAnual(meses: List<PresupuestoConGastos>, anios: List<Int>): ResumenAnual {
        val gasto = mutableMapOf<Int, Float>()
        val ahorro = mutableMapOf<Int, Float>()
        val ingreso = mutableMapOf<Int, Float>()
        anios.forEach { anio ->
            val mesesDelAnio = meses.filter { it.estado == EstadoPresupuesto.CERRADO && it.anio == anio }
            ingreso[anio] = mesesDelAnio.sumOf { it.dineroDisponible }.toFloat()
            gasto[anio] = mesesDelAnio.sumOf { mes -> mes.categorias.sumOf { sumaGastosReales(it.gastos) } }.toFloat()
            ahorro[anio] = mesesDelAnio.sumOf { ahorroDelMes(it) }.toFloat()
        }
        return ResumenAnual(gasto, ahorro, ingreso)
    }
}