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
    val esIngreso: Boolean = false,
    /** true si esta fila es el apunte de SALIDA que cerrarMesConReparto()
     *  registra en la categoría de ORIGEN al traspasar su sobrante (para
     *  dejar su "restante" en 0). No es un gasto real del usuario: debe
     *  seguir sumando dentro de "gastado" de su categoría (por eso sigue
     *  con esIngreso = false), pero debe EXCLUIRSE de cualquier cálculo
     *  agregado de "gasto total" (gráficas, resúmenes), donde inflaría el
     *  gasto real exactamente por el importe que en realidad es ahorro. */
    val esTraspasoSalida: Boolean = false
)
