package com.warthogcash.presupuesto.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.warthogcash.presupuesto.data.entity.GastoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {

    @Insert
    suspend fun insertar(gasto: GastoEntity): Long

    @Query("SELECT * FROM gastos WHERE categoriaId = :categoriaId ORDER BY fecha DESC")
    suspend fun obtenerPorCategoria(categoriaId: Long): List<GastoEntity>

    @Query("SELECT * FROM gastos WHERE categoriaId IN (:categoriaIds) ORDER BY fecha DESC")
    suspend fun obtenerPorCategorias(categoriaIds: List<Long>): List<GastoEntity>

    @Query("SELECT * FROM gastos WHERE categoriaId IN (:categoriaIds) ORDER BY fecha DESC")
    fun observarPorCategorias(categoriaIds: List<Long>): Flow<List<GastoEntity>>

    @Query("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId = :categoriaId")
    suspend fun sumarPorCategoria(categoriaId: Long): Double

    @Query("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId IN (:categoriaIds)")
    suspend fun sumarPorCategorias(categoriaIds: List<Long>): Double
}
