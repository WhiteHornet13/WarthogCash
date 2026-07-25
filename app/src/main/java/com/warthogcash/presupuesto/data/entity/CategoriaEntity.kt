package com.warthogcash.presupuesto.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Una de las 5 categorías fijas dentro de un Presupuesto: tipo (enum),
 * porcentaje asignado. Especificación técnica, sección 5.1/5.2.
 * El nombre de categoría (tipo) se guarda como texto (nombre del enum
 * [com.warthogcash.presupuesto.domain.model.TipoCategoria]), nunca como
 * catálogo editable en base de datos.
 */
@Entity(
    tableName = "categorias",
    foreignKeys = [
        ForeignKey(
            entity = PresupuestoEntity::class,
            parentColumns = ["id"],
            childColumns = ["presupuestoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("presupuestoId")]
)
data class CategoriaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val presupuestoId: Long,
    val tipo: String,
    val porcentaje: Double
)
