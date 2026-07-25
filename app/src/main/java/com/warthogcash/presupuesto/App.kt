package com.warthogcash.presupuesto

import android.app.Application
import com.warthogcash.presupuesto.data.AppDatabase
import com.warthogcash.presupuesto.data.repository.PresupuestoRepositoryImpl
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository

/**
 * Punto único de instanciación manual de dependencias.
 *
 * La especificación técnica deja pendiente (sección 8) si se usará un
 * framework de inyección de dependencias o instanciación manual. Se opta
 * aquí por instanciación manual mediante esta clase Application a modo de
 * contenedor simple, por ser la opción de menor complejidad para el
 * alcance actual del proyecto. Revisar si se decide adoptar Hilt/Koin
 * más adelante.
 */
class App : Application() {

    val repository: PresupuestoRepository by lazy {
        val db = AppDatabase.obtenerInstancia(this)
        PresupuestoRepositoryImpl(db.presupuestoDao(), db.categoriaDao(), db.gastoDao())
    }
}
