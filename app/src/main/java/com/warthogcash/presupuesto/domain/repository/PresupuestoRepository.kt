package com.warthogcash.presupuesto.domain.repository

import com.warthogcash.presupuesto.domain.model.GastoDetallado
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import kotlinx.coroutines.flow.Flow
import com.warthogcash.presupuesto.domain.model.GastoFijo
import com.warthogcash.presupuesto.domain.model.GastoFijoAplicado

/**
 * Contrato del repositorio de presupuestos. La interfaz de usuario nunca
 * depende directamente de Room; solo conoce esta interfaz
 * (especificación técnica, sección 5.4).
 */
interface PresupuestoRepository {

    /** true si no existe ningún mes creado todavía (condición de la pantalla Bienvenida). */
    suspend fun existeAlgunMes(): Boolean

    /** true si ya existe un mes creado con ese mes/año (evita duplicados). */
    suspend fun existeMes(mes: Int, anio: Int): Boolean

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

    /** true si existe un Presupuesto creado para el mes calendario INMEDIATAMENTE
     *  siguiente a [presupuestoId] (mes+1, con ajuste de año) y ese mes está ABIERTO.
     *  Solo en ese caso "Cerrar mes" permite elegir, categoría a categoría, si su
     *  sobrante pasa a ese mes siguiente o se suma a Ahorro de este mismo mes.
     *  No basta con que exista "algún" mes posterior (p. ej. mayo no puede pasar
     *  a agosto si junio no existe: en ese caso todo va a Ahorro). */
    suspend fun existeMesSiguienteInmediatoAbierto(presupuestoId: Long): Boolean

    /**
     * Cierra un mes repartiendo su sobrante:
     * - Si [presupuestoId] es el mes inmediatamente anterior al actual, el sobrante de las
     *   categorías cuyo id esté en [categoriasATraspasar] se suma a la misma categoría del mes
     *   siguiente (si existe).
     * - El sobrante de cualquier otra categoría —no marcada, sin mes siguiente creado, o de un
     *   mes que no es el inmediatamente anterior al actual— se suma a Ahorro de este mismo mes.
     * - Ahorro nunca traspasa a sí mismo.
     */
    suspend fun cerrarMesConReparto(presupuestoId: Long, categoriasATraspasar: Set<Long>)

    /** Registra un gasto en una categoría; devuelve el id generado. */
    suspend fun agregarGasto(categoriaId: Long, importe: Double, descripcion: String?): Long

    suspend fun obtenerGastosDeMes(presupuestoId: Long): List<GastoDetallado>

    suspend fun obtenerGastosDeMesFiltrados(presupuestoId: Long, tipo: TipoCategoria): List<GastoDetallado>

    /** Edita importe y descripción de un gasto existente; recalcula el gastado de su categoría. */
    suspend fun editarGasto(gastoId: Long, importe: Double, descripcion: String?)

    /** Elimina un gasto; recalcula el gastado de su categoría. */
    suspend fun eliminarGasto(gastoId: Long)

    /** true si existe un mes "actual" distinto del indicado (spec detalle mes anterior, 4.3). */
    suspend fun existeMesActualDistintoDe(presupuestoId: Long): Boolean

    // --- Gastos fijos ---------------------------------------------------

    /** true si existe al menos un gasto fijo definido (decide si se muestra
     * la pantalla "Seleccionar gastos fijos" al crear un mes). */
    suspend fun existenGastosFijos(): Boolean

    suspend fun obtenerGastosFijos(): List<GastoFijo>

    fun observarGastosFijos(): Flow<List<GastoFijo>>

    suspend fun crearGastoFijo(coste: Double, tipo: TipoCategoria, comentario: String?): Long

    suspend fun actualizarGastoFijo(id: Long, coste: Double, tipo: TipoCategoria, comentario: String?)

    suspend fun eliminarGastoFijo(id: Long)

    /** Aplica al mes [mesId] los gastos fijos seleccionados, creando un
     * Gasto normal por cada uno en su categoría correspondiente. */
    suspend fun aplicarGastosFijosAMes(mesId: Long, seleccionados: List<GastoFijoAplicado>)
}
