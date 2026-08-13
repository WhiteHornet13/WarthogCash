package com.warthogcash.presupuesto.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.warthogcash.presupuesto.data.entity.GastoEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.Delete
import androidx.room.Update

@Dao
interface GastoDao {

    @Insert
    suspend fun insertar(gasto: GastoEntity): Long

    @Update
    suspend fun actualizar(gasto: GastoEntity)

    @Delete
    suspend fun eliminar(gasto: GastoEntity)

    @Query("SELECT * FROM gastos WHERE id = :id")
    suspend fun obtenerPorId(id: Long): GastoEntity?

    /** Filas de cobertura automática en Ahorro generadas por [gastoId].
     *  Se usa para borrarlas en cascada al eliminar el gasto que las originó. */
    @Query("SELECT * FROM gastos WHERE gastoCoberturaOrigenId = :gastoId")
    suspend fun obtenerPorGastoCoberturaOrigen(gastoId: Long): List<GastoEntity>

    @Query("SELECT * FROM gastos WHERE categoriaId = :categoriaId ORDER BY fecha DESC")
    suspend fun obtenerPorCategoria(categoriaId: Long): List<GastoEntity>

    @Query("SELECT * FROM gastos WHERE categoriaId IN (:categoriaIds) ORDER BY fecha DESC")
    suspend fun obtenerPorCategorias(categoriaIds: List<Long>): List<GastoEntity>

    @Query("SELECT * FROM gastos WHERE categoriaId IN (:categoriaIds) ORDER BY fecha DESC")
    fun observarPorCategorias(categoriaIds: List<Long>): Flow<List<GastoEntity>>

    /** Gasto "real" a efectos de cálculo: gastos normales + traspasos de sobrante
     *  que fueron al mes SIGUIENTE (cuentan como gasto real). Excluye los
     *  traspasos que fueron a Ahorro (mesOrigenId nulo): eso es ahorro, no gasto. */
    @Query("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId = :categoriaId AND esIngreso = 0 AND NOT (esTraspasoSalida = 1 AND mesOrigenId IS NULL)")
    suspend fun sumarPorCategoria(categoriaId: Long): Double

    @Query("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId IN (:categoriaIds) AND esIngreso = 0 AND NOT (esTraspasoSalida = 1 AND mesOrigenId IS NULL)")
    suspend fun sumarPorCategorias(categoriaIds: List<Long>): Double

    /** Sobrante que esta categoría traspasó a Ahorro (mismo mes) al cerrarse.
     *  No es gasto; se usa para el estado "Sobrante traspasado" y para
     *  descontarlo del restante real. */
    @Query("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId = :categoriaId AND esTraspasoSalida = 1 AND mesOrigenId IS NULL")
    suspend fun sumarTraspasadoAAhorroPorCategoria(categoriaId: Long): Double

    /** Sobrante que esta categoría traspasó al mes SIGUIENTE al cerrarse.
     *  Ya está incluido dentro de sumarPorCategoria() (cuenta como gasto real
     *  a efectos de cálculo), pero se necesita aparte para mostrar el estado
     *  "Restante traspasado" en vez de "Límite superado". */
    @Query("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId = :categoriaId AND esTraspasoSalida = 1 AND mesOrigenId IS NOT NULL")
    suspend fun sumarTraspasadoOtroMesPorCategoria(categoriaId: Long): Double


    /** Ingresos por traspaso (esIngreso = 1) recibidos por una categoría; se suman
     *  al monto asignado, nunca al gastado. */
    @Query("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId = :categoriaId AND esIngreso = 1")
    suspend fun sumarIngresosPorCategoria(categoriaId: Long): Double

    /** Subconjunto de lo anterior: solo ingresos recibidos de OTRO mes
     *  (mesOrigenId no nulo, traspaso real entre meses al cerrarse el mes
     *  anterior). Excluye los traspasos internos a Ahorro dentro del mismo
     *  mes (mesOrigenId nulo), que no son dinero nuevo, sino una
     *  reubicación del mismo dineroDisponible del mes. */
    @Query("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId = :categoriaId AND esIngreso = 1 AND mesOrigenId IS NOT NULL")
    suspend fun sumarIngresosDeOtroMesPorCategoria(categoriaId: Long): Double
}
