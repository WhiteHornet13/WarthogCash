package com.warthogcash.presupuesto.domain.repository

import com.warthogcash.presupuesto.domain.model.GastoDetallado
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import kotlinx.coroutines.flow.Flow

/**
 * Contrato del repositorio de presupuestos. La interfaz de usuario nunca
 * depende directamente de Room; solo conoce esta interfaz
 * (especificación técnica, sección 5.4).
 */
interface PresupuestoRepository {

    /** true si no existe ningún mes creado todavía (condición de la pantalla Bienvenida). */
    suspend fun existeAlgunMes(): Boolean

    suspend fun obtenerMesActual(): Presupuesto?

    /** Observa cambios en el mes actual (usado por la Pantalla principal). */
    fun observarMesActual(): Flow<Presupuesto?>

    suspend fun obtenerMesPorId(id: Long): Presupuesto?

    fun observarMesPorId(id: Long): Flow<Presupuesto?>

    /** Página de meses ordenados de más reciente a más antiguo, para "Mis meses". */
    suspend fun obtenerPaginaMeses(limite: Int, offset: Int): List<Presupuesto>

    /**
     * Crea un nuevo mes con sus 5 categorías fijas y sus porcentajes.
     * El nuevo mes pasa a ser el "actual"; el mes que lo era hasta ahora
     * permanece abierto (especificación "Pantalla principal", sección 4.6).
     */
    suspend fun crearMes(
        mes: Int,
        anio: Int,
        dineroDisponible: Double,
        porcentajes: Map<TipoCategoria, Double>
    ): Long

    /** Cierra un mes (no aplicable al mes actual, ver spec "Mis meses" 4.6). */
    suspend fun cerrarMes(presupuestoId: Long)

    /** Registra un gasto en una categoría; devuelve el id generado. */
    suspend fun agregarGasto(categoriaId: Long, importe: Double, descripcion: String?): Long

    suspend fun obtenerGastosDeMes(presupuestoId: Long): List<GastoDetallado>

    suspend fun obtenerGastosDeMesFiltrados(presupuestoId: Long, tipo: TipoCategoria): List<GastoDetallado>

    /** true si existe un mes "actual" distinto del indicado (spec detalle mes anterior, 4.3). */
    suspend fun existeMesActualDistintoDe(presupuestoId: Long): Boolean
}
