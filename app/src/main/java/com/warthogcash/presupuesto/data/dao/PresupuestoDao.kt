package com.warthogcash.presupuesto.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.warthogcash.presupuesto.data.entity.PresupuestoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PresupuestoDao {

    @Insert
    suspend fun insertar(presupuesto: PresupuestoEntity): Long

    @Update
    suspend fun actualizar(presupuesto: PresupuestoEntity)

    @Query("SELECT COUNT(*) FROM presupuestos")
    suspend fun contarMeses(): Int

    @Query("SELECT * FROM presupuestos WHERE esActual = 1 LIMIT 1")
    suspend fun obtenerActual(): PresupuestoEntity?

    @Query("SELECT * FROM presupuestos WHERE esActual = 1 LIMIT 1")
    fun observarActual(): Flow<PresupuestoEntity?>

    @Query("SELECT * FROM presupuestos WHERE id = :id")
    suspend fun obtenerPorId(id: Long): PresupuestoEntity?

    @Query("SELECT * FROM presupuestos WHERE id = :id")
    fun observarPorId(id: Long): Flow<PresupuestoEntity?>

    @Query("UPDATE presupuestos SET esActual = 0 WHERE esActual = 1")
    suspend fun limpiarActual()

    @Query("UPDATE presupuestos SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstado(id: Long, estado: String)

    @Query("SELECT * FROM presupuestos ORDER BY anio DESC, mes DESC LIMIT :limite OFFSET :offset")
    suspend fun obtenerPagina(limite: Int, offset: Int): List<PresupuestoEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM presupuestos WHERE esActual = 1 AND id != :presupuestoId)")
    suspend fun existeActualDistintoDe(presupuestoId: Long): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM presupuestos WHERE mes = :mes AND anio = :anio)")
    suspend fun existeMes(mes: Int, anio: Int): Boolean
}
