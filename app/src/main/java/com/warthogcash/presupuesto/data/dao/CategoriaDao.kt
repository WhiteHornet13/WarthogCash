package com.warthogcash.presupuesto.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.warthogcash.presupuesto.data.entity.CategoriaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    @Insert
    suspend fun insertarTodas(categorias: List<CategoriaEntity>): List<Long>

    @Query("SELECT * FROM categorias WHERE presupuestoId = :presupuestoId")
    suspend fun obtenerPorPresupuesto(presupuestoId: Long): List<CategoriaEntity>

    @Query("SELECT * FROM categorias WHERE presupuestoId = :presupuestoId")
    fun observarPorPresupuesto(presupuestoId: Long): Flow<List<CategoriaEntity>>

    @Query("SELECT * FROM categorias WHERE id = :id")
    suspend fun obtenerPorId(id: Long): CategoriaEntity?

    @Query("SELECT * FROM categorias WHERE presupuestoId = :presupuestoId AND tipo = :tipo LIMIT 1")
    suspend fun obtenerPorPresupuestoYTipo(presupuestoId: Long, tipo: String): CategoriaEntity?
}
