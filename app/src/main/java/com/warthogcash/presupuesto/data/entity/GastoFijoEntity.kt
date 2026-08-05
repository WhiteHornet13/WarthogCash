package com.warthogcash.presupuesto.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Un gasto fijo definido por el usuario (plantilla reutilizable mes a
 * mes): coste de referencia, categoría y comentario opcional. No está
 * ligado a ningún Presupuesto concreto; se aplica a un mes generando un
 * GastoEntity normal en la categoría correspondiente (ver pantalla
 * "Seleccionar gastos fijos" dentro del flujo de "Crear mes nuevo").
 */
@Entity(tableName = "gastos_fijos")
data class GastoFijoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val coste: Double,
    val tipo: String,
    val comentario: String?
)