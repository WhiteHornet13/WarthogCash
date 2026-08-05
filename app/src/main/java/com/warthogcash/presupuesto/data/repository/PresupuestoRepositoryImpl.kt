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

/**
 * Implementación del repositorio: traduce entre entidades Room y modelos
 * de dominio (especificación técnica, sección 5.4). Es la única capa que
 * conoce simultáneamente Room y el dominio.
 */
class PresupuestoRepositoryImpl(
    private val presupuestoDao: PresupuestoDao,
    private val categoriaDao: CategoriaDao,
    private val gastoDao: GastoDao
) : PresupuestoRepository {

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

    override suspend fun cerrarMes(presupuestoId: Long) {
        presupuestoDao.actualizarEstado(presupuestoId, EstadoPresupuesto.CERRADO.name)
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

    override suspend fun existeMesActualDistintoDe(presupuestoId: Long): Boolean =
        presupuestoDao.existeActualDistintoDe(presupuestoId)

    // --- Helpers de mapeo Room -> dominio -----------------------------------

    private suspend fun mapearPresupuestoCompleto(entidad: PresupuestoEntity): Presupuesto {
        val categoriasEntity = categoriaDao.obtenerPorPresupuesto(entidad.id)
        val categorias = categoriasEntity.map { catEntity ->
            val gastado = gastoDao.sumarPorCategoria(catEntity.id)
            catEntity.aDominio(entidad.dineroDisponible, gastado)
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

    private fun CategoriaEntity.aDominio(dineroDisponibleMes: Double, gastado: Double): Categoria = Categoria(
        id = id,
        presupuestoId = presupuestoId,
        tipo = TipoCategoria.valueOf(tipo),
        porcentaje = porcentaje,
        montoAsignado = dineroDisponibleMes * (porcentaje / 100.0),
        gastado = gastado
    )

    private fun GastoEntity.aDominio(): Gasto = Gasto(
        id = id,
        categoriaId = categoriaId,
        importe = importe,
        descripcion = descripcion,
        fecha = fecha
    )
}
