package com.warthogcash.presupuesto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.warthogcash.presupuesto.data.dao.CategoriaDao
import com.warthogcash.presupuesto.data.dao.GastoDao
import com.warthogcash.presupuesto.data.dao.PresupuestoDao
import com.warthogcash.presupuesto.data.entity.CategoriaEntity
import com.warthogcash.presupuesto.data.entity.GastoEntity
import com.warthogcash.presupuesto.data.entity.PresupuestoEntity

/**
 * Instancia central de la base de datos Room (especificación técnica, 5.4).
 * Estrategia de migraciones pendiente de definir (sección 8); de momento,
 * al no haber versiones publicadas, no se requiere ninguna.
 */
@Database(
    entities = [PresupuestoEntity::class, CategoriaEntity::class, GastoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun presupuestoDao(): PresupuestoDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun gastoDao(): GastoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "presupuesto_personal.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
