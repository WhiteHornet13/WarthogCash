package com.warthogcash.presupuesto.data.repository

import com.warthogcash.presupuesto.data.dao.CategoriaDao
import com.warthogcash.presupuesto.data.dao.GastoDao
import com.warthogcash.presupuesto.data.dao.PresupuestoDao
import com.warthogcash.presupuesto.data.entity.CategoriaEntity
import com.warthogcash.presupuesto.data.entity.GastoEntity
import com.warthogcash.presupuesto.data.entity.PresupuestoEntity
import com.warthogcash.presupuesto.domain.model.Categoria
import com.warthogcash.presupuesto.domain.model.EstadoPresupuesto
import com.warthogcash.presupuesto.domain.model.Gasto
import com.warthogcash.presupuesto.domain.model.GastoDetallado
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.warthogcash.presupuesto.data.dao.GastoFijoDao
import com.warthogcash.presupuesto.data.entity.GastoFijoEntity
import com.warthogcash.presupuesto.domain.model.GastoFijo
import com.warthogcash.presupuesto.domain.model.GastoFijoAplicado

/**
 * Implementación del repositorio: traduce entre entidades Room y modelos
 * de dominio (especificación técnica, sección 5.4). Es la única capa que
 * conoce simultáneamente Room y el dominio.
 */
class PresupuestoRepositoryImpl(
    private val presupuestoDao: PresupuestoDao,
    private val categoriaDao: CategoriaDao,
    private val gastoDao: GastoDao,
    private val gastoFijoDao: GastoFijoDao
) : PresupuestoRepository {

    // --- Gastos fijos ---------------------------------------------------

    override suspend fun existenGastosFijos(): Boolean = gastoFijoDao.contar() > 0

    override suspend fun obtenerGastosFijos(): List<GastoFijo> =
        gastoFijoDao.obtenerTodos().map { it.aDominio() }

    override fun observarGastosFijos(): Flow<List<GastoFijo>> =
        gastoFijoDao.observarTodos().map { lista -> lista.map { it.aDominio() } }

    override suspend fun crearGastoFijo(coste: Double, tipo: TipoCategoria, comentario: String?): Long =
        gastoFijoDao.insertar(GastoFijoEntity(coste = coste, tipo = tipo.name, comentario = comentario))

    override suspend fun actualizarGastoFijo(id: Long, coste: Double, tipo: TipoCategoria, comentario: String?) {
        gastoFijoDao.actualizar(GastoFijoEntity(id = id, coste = coste, tipo = tipo.name, comentario = comentario))
    }

    override suspend fun eliminarGastoFijo(id: Long) {
        gastoFijoDao.obtenerPorId(id)?.let { gastoFijoDao.eliminar(it) }
    }

    override suspend fun aplicarGastosFijosAMes(mesId: Long, seleccionados: List<GastoFijoAplicado>) {
        seleccionados.forEach { aplicado ->
            val categoria = categoriaDao.obtenerPorPresupuestoYTipo(mesId, aplicado.tipo.name) ?: return@forEach
            gastoDao.insertar(
                GastoEntity(
                    categoriaId = categoria.id,
                    importe = aplicado.coste,
                    descripcion = aplicado.comentario,
                    fecha = System.currentTimeMillis()
                )
            )
        }
    }

    private fun GastoFijoEntity.aDominio(): GastoFijo = GastoFijo(
        id = id,
        coste = coste,
        tipo = TipoCategoria.valueOf(tipo),
        comentario = comentario
    )

    override suspend fun existeAlgunMes(): Boolean = presupuestoDao.contarMeses() > 0

    override suspend fun obtenerMesActual(): Presupuesto? {
        val entidad = presupuestoDao.obtenerActual() ?: return null
        return mapearPresupuestoCompleto(entidad)
    }

    override fun observarMesActual(): Flow<Presupuesto?> =
        presupuestoDao.observarActual().map { entidad ->
            entidad?.let { mapearPresupuestoCompleto(it) }
        }

    override suspend fun obtenerMesPorId(id: Long): Presupuesto? {
        val entidad = presupuestoDao.obtenerPorId(id) ?: return null
        return mapearPresupuestoCompleto(entidad)
    }

    override fun observarMesPorId(id: Long): Flow<Presupuesto?> =
        presupuestoDao.observarPorId(id).map { entidad ->
            entidad?.let { mapearPresupuestoCompleto(it) }
        }

    override suspend fun obtenerPaginaMeses(limite: Int, offset: Int): List<Presupuesto> {
        val entidades = presupuestoDao.obtenerPagina(limite, offset)
        return entidades.map { mapearPresupuestoCompleto(it) }
    }

    override suspend fun crearMes(
        mes: Int,
        anio: Int,
        dineroDisponible: Double,
        porcentajes: Map<TipoCategoria, Double>
    ): Long {
        fun indiceAbsoluto(a: Int, m: Int) = a * 12 + m
        val indiceNuevo = indiceAbsoluto(anio, mes)

        val actualExistente = presupuestoDao.obtenerActual()

        // Un mes nuevo solo pasa a ser "actual" si no hay ninguno todavía
        // (primer mes de la app) o si es exactamente el siguiente al que
        // es actual en este momento. Cualquier otro mes se crea como
        // ABIERTO normal, sin tocar cuál es el mes actual.
        val debeSerActual = if (actualExistente == null) {
            true
        } else {
            indiceNuevo == indiceAbsoluto(actualExistente.anio, actualExistente.mes) + 1
        }

        if (debeSerActual) {
            presupuestoDao.limpiarActual()
        }

        val nuevoId = presupuestoDao.insertar(
            PresupuestoEntity(
                mes = mes,
                anio = anio,
                dineroDisponible = dineroDisponible,
                estado = EstadoPresupuesto.ABIERTO.name,
                esActual = debeSerActual
            )
        )

        val categorias = TipoCategoria.ORDEN_VISUAL.map { tipo ->
            CategoriaEntity(
                presupuestoId = nuevoId,
                tipo = tipo.name,
                porcentaje = porcentajes[tipo] ?: 0.0
            )
        }
        categoriaDao.insertarTodas(categorias)

        return nuevoId
    }

    override suspend fun existeMesSiguienteInmediatoAbierto(presupuestoId: Long): Boolean {
        val mesEntity = presupuestoDao.obtenerPorId(presupuestoId) ?: return false
        val siguiente = obtenerEntidadMesSiguiente(mesEntity) ?: return false
        return siguiente.estado == EstadoPresupuesto.ABIERTO.name
    }

    override suspend fun cerrarMesConReparto(presupuestoId: Long, categoriasATraspasar: Set<Long>) {
        val mesEntity = presupuestoDao.obtenerPorId(presupuestoId) ?: return
        // Se usa el Presupuesto ya mapeado para que "restante" de cada categoría
        // tenga en cuenta traspasos que este mes ya haya recibido de su propio
        // mes anterior, no solo el % base.
        val mes = mapearPresupuestoCompleto(mesEntity)
        val categoriaAhorroEntity = categoriaDao.obtenerPorPresupuestoYTipo(presupuestoId, TipoCategoria.AHORRO.name)

        val siguienteMesEntity = obtenerEntidadMesSiguiente(mesEntity)
            ?.takeIf { it.estado == EstadoPresupuesto.ABIERTO.name }
        val permiteTraspaso = siguienteMesEntity != null
        val categoriasSiguienteMes = siguienteMesEntity?.let { categoriaDao.obtenerPorPresupuesto(it.id) } ?: emptyList()

        mes.categorias.forEach { categoria ->
            if (categoria.tipo == TipoCategoria.AHORRO) return@forEach // Ahorro no traspasa a sí mismo
            val sobrante = categoria.restante
            if (sobrante <= 0.0) return@forEach

            val traspasaAlSiguiente = permiteTraspaso && categoria.id in categoriasATraspasar
            val categoriaDestinoSiguiente = if (traspasaAlSiguiente) {
                categoriasSiguienteMes.firstOrNull { it.tipo == categoria.tipo.name }
            } else null

            if (categoriaDestinoSiguiente != null) {
                // Traspaso real al mes siguiente: ingreso (esIngreso = true), NUNCA
                // un gasto negativo. Se suma al monto asignado de esa categoría,
                // no resta del gastado.
                gastoDao.insertar(
                    GastoEntity(
                        categoriaId = categoriaDestinoSiguiente.id,
                        importe = sobrante,
                        descripcion = "Traspaso de ${categoria.tipo.etiqueta} de ${mes.nombreMesAnio}",
                        fecha = System.currentTimeMillis(),
                        esIngreso = true
                    )
                )
            } else if (categoriaAhorroEntity != null) {
                // Una fila de ingreso INDEPENDIENTE por cada categoría de origen,
                // no un total agregado: así el historial de Ahorro muestra de
                // dónde viene cada cantidad.
                gastoDao.insertar(
                    GastoEntity(
                        categoriaId = categoriaAhorroEntity.id,
                        importe = sobrante,
                        descripcion = "Sobrante de ${categoria.tipo.etiqueta} de ${mes.nombreMesAnio}",
                        fecha = System.currentTimeMillis(),
                        esIngreso = true
                    )
                )
            }

            // NUEVO: registra la SALIDA de ese sobrante en la categoría de ORIGEN.
            // Sin esto, la categoría origen seguía mostrando el sobrante como
            // "restante" tras el cierre, aunque ese mismo dinero ya apareciera
            // también en el destino (doble conteo). Se marca como gasto real
            // (esIngreso = false) para que "restante" de la categoría origen
            // quede en 0, reflejando que ese dinero ya salió de ella.
            gastoDao.insertar(
                GastoEntity(
                    categoriaId = categoria.id,
                    importe = sobrante,
                    descripcion = if (categoriaDestinoSiguiente != null)
                        "Traspasado a ${categoria.tipo.etiqueta} de mes siguiente"
                    else
                        "Traspasado a Ahorro",
                    fecha = System.currentTimeMillis(),
                    esIngreso = false
                )
            )
        }



        presupuestoDao.actualizarEstado(presupuestoId, EstadoPresupuesto.CERRADO.name)
    }

    /** Busca el Presupuesto del mes calendario exactamente siguiente a [mesEntity]
     *  (mes+1, con ajuste de año). Devuelve null si ese mes concreto no existe,
     *  aunque existan meses posteriores más lejanos. */
    private suspend fun obtenerEntidadMesSiguiente(mesEntity: PresupuestoEntity): PresupuestoEntity? {
        fun indiceAbsoluto(a: Int, m: Int) = a * 12 + m
        val indiceSiguiente = indiceAbsoluto(mesEntity.anio, mesEntity.mes) + 1
        val anioSiguiente = (indiceSiguiente - 1) / 12
        val mesSiguiente = indiceSiguiente - anioSiguiente * 12
        return presupuestoDao.obtenerPorMesYAnio(mesSiguiente, anioSiguiente)
    }

    override suspend fun existeMes(mes: Int, anio: Int): Boolean =
        presupuestoDao.existeMes(mes, anio)

    override suspend fun agregarGasto(categoriaId: Long, importe: Double, descripcion: String?): Long {
        return gastoDao.insertar(
            GastoEntity(
                categoriaId = categoriaId,
                importe = importe,
                descripcion = descripcion,
                fecha = System.currentTimeMillis()
            )
        )
    }

    override suspend fun obtenerGastosDeMes(presupuestoId: Long): List<GastoDetallado> {
        val categorias = categoriaDao.obtenerPorPresupuesto(presupuestoId)
        val tipoPorCategoriaId = categorias.associate { it.id to TipoCategoria.valueOf(it.tipo) }
        val ids = categorias.map { it.id }
        if (ids.isEmpty()) return emptyList()
        return gastoDao.obtenerPorCategorias(ids).map { entidad ->
            GastoDetallado(entidad.aDominio(), tipoPorCategoriaId.getValue(entidad.categoriaId))
        }
    }

    override suspend fun obtenerGastosDeMesFiltrados(presupuestoId: Long, tipo: TipoCategoria): List<GastoDetallado> {
        val categoria = categoriaDao.obtenerPorPresupuestoYTipo(presupuestoId, tipo.name) ?: return emptyList()
        return gastoDao.obtenerPorCategoria(categoria.id).map { entidad ->
            GastoDetallado(entidad.aDominio(), tipo)
        }
    }

    override suspend fun editarGasto(gastoId: Long, importe: Double, descripcion: String?) {
        val entidad = gastoDao.obtenerPorId(gastoId) ?: return
        gastoDao.actualizar(entidad.copy(importe = importe, descripcion = descripcion))
    }

    override suspend fun eliminarGasto(gastoId: Long) {
        val entidad = gastoDao.obtenerPorId(gastoId) ?: return
        gastoDao.eliminar(entidad)
    }

    override suspend fun existeMesActualDistintoDe(presupuestoId: Long): Boolean =
        presupuestoDao.existeActualDistintoDe(presupuestoId)

    // --- Helpers de mapeo Room -> dominio -----------------------------------

    private suspend fun mapearPresupuestoCompleto(entidad: PresupuestoEntity): Presupuesto {
        val categoriasEntity = categoriaDao.obtenerPorPresupuesto(entidad.id)
        val categorias = categoriasEntity.map { catEntity ->
            val gastado = gastoDao.sumarPorCategoria(catEntity.id)
            val ingresos = gastoDao.sumarIngresosPorCategoria(catEntity.id)
            catEntity.aDominio(entidad.dineroDisponible, gastado, ingresos)
        }
        return entidad.aDominio(categorias)
    }

    private fun PresupuestoEntity.aDominio(categorias: List<Categoria>): Presupuesto = Presupuesto(
        id = id,
        mes = mes,
        anio = anio,
        dineroDisponible = dineroDisponible,
        estado = EstadoPresupuesto.valueOf(estado),
        esActual = esActual,
        categorias = categorias
    )

    private fun CategoriaEntity.aDominio(dineroDisponibleMes: Double, gastado: Double, ingresosTraspasados: Double): Categoria = Categoria(
        id = id,
        presupuestoId = presupuestoId,
        tipo = TipoCategoria.valueOf(tipo),
        porcentaje = porcentaje,
        montoAsignadoBase = dineroDisponibleMes * (porcentaje / 100.0),
        ingresosTraspasados = ingresosTraspasados,
        gastado = gastado
    )

    private fun GastoEntity.aDominio(): Gasto = Gasto(
        id = id,
        categoriaId = categoriaId,
        importe = importe,
        descripcion = descripcion,
        fecha = fecha,
        esIngreso = esIngreso
    )
}
