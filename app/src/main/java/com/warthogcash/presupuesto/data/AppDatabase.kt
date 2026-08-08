package com.warthogcash.presupuesto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.warthogcash.presupuesto.data.dao.CategoriaDao
import com.warthogcash.presupuesto.data.dao.GastoDao
import com.warthogcash.presupuesto.data.dao.GastoFijoDao
import com.warthogcash.presupuesto.data.dao.PresupuestoDao
import com.warthogcash.presupuesto.data.entity.CategoriaEntity
import com.warthogcash.presupuesto.data.entity.GastoEntity
import com.warthogcash.presupuesto.data.entity.GastoFijoEntity
import com.warthogcash.presupuesto.data.entity.PresupuestoEntity

/**
 * Instancia central de la base de datos Room (especificación técnica, 5.4).
 * Versión 2: añade la tabla `gastos_fijos` (funcionalidad "Gastos fijos").
 */
@Database(
    entities = [PresupuestoEntity::class, CategoriaEntity::class, GastoEntity::class, GastoFijoEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun presupuestoDao(): PresupuestoDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun gastoDao(): GastoDao
    abstract fun gastoFijoDao(): GastoFijoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `gastos_fijos` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `coste` REAL NOT NULL,
                        `tipo` TEXT NOT NULL,
                        `comentario` TEXT
                    )
                    """.trimIndent()
                )
            }
        }

        private val MIGRACION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE gastos ADD COLUMN esIngreso INTEGER NOT NULL DEFAULT 0")
            }
        }

        private val MIGRACION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE gastos ADD COLUMN esTraspasoSalida INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun obtenerInstancia(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "presupuesto_personal.db"
                ).addMigrations(MIGRACION_1_2, MIGRACION_2_3, MIGRACION_3_4).build().also { INSTANCE = it }
            }
        }
    }
}