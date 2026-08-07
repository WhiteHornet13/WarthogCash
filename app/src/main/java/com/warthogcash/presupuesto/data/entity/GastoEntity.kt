package com.warthogcash.presupuesto.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Un gasto individual, ligado a una Categoria. Especificación técnica,
 * sección 5.1. Los importes "gastado"/"restante" por categoría NO se
 * almacenan (sección 5.3); se derivan sumando estas filas.
 */
@Entity(
    tableName = "gastos",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoriaId")]
)
data class GastoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoriaId: Long,
    val importe: Double,
    val descripcion: String?,
    val fecha: Long,
    /** true si esta fila es un ingreso (traspaso de sobrante recibido de otra
     *  categoría/mes), no un gasto real. Se excluye del cálculo de "gastado"
     *  y se suma al monto asignado de la categoría. */
    val esIngreso: Boolean = false
)
