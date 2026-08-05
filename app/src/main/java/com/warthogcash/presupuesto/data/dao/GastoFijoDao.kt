package com.warthogcash.presupuesto.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.warthogcash.presupuesto.data.entity.GastoFijoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoFijoDao {

    @Insert
    suspend fun insertar(gastoFijo: GastoFijoEntity): Long

    @Update
    suspend fun actualizar(gastoFijo: GastoFijoEntity)

    @Delete
    suspend fun eliminar(gastoFijo: GastoFijoEntity)

    @Query("SELECT * FROM gastos_fijos ORDER BY id DESC")
    suspend fun obtenerTodos(): List<GastoFijoEntity>

    @Query("SELECT * FROM gastos_fijos ORDER BY id DESC")
    fun observarTodos(): Flow<List<GastoFijoEntity>>

    @Query("SELECT COUNT(*) FROM gastos_fijos")
    suspend fun contar(): Int

    @Query("SELECT * FROM gastos_fijos WHERE id = :id")
    suspend fun obtenerPorId(id: Long): GastoFijoEntity?
}