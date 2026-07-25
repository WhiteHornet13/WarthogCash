package com.warthogcash.presupuesto.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Un mes concreto: mes, año, dinero disponible, estado (abierto/cerrado).
 * Especificación técnica, sección 5.1.
 *
 * [esActual] marca cuál es el mes "actual" de la app (a lo sumo uno true
 * a la vez). No aparece nombrado como campo propio en la especificación
 * técnica, pero es necesario para implementar la regla de negocio de
 * "mes actual" descrita repetidamente en las especificaciones de pantalla
 * (Pantalla principal 4.6, Mis meses 4.4/4.6).
 */
@Entity(tableName = "presupuestos")
data class PresupuestoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mes: Int,
    val anio: Int,
    val dineroDisponible: Double,
    val estado: String,
    val esActual: Boolean
)
