package com.warthogcash.presupuesto.util

import com.warthogcash.presupuesto.domain.model.PresupuestoConGastos

/**
 * CSV "aplanado" del historial de gastos (fecha, mes, categoría, tipo,
 * importe, descripción), pensado para Excel/OpenOffice. No sirve como
 * copia de seguridad real: para eso está BackupJson (aquí se pierden el
 * % de cada categoría y las relaciones jerárquicas).
 */
object CsvExporter {

    // ";" en vez de "," : evita conflicto con el separador decimal "," del
    // formato es-ES que ya usa Formato.importeEditable().
    private const val SEPARADOR = ';'

    fun generar(meses: List<PresupuestoConGastos>): String {
        val sb = StringBuilder()
        sb.append("Fecha").append(SEPARADOR)
            .append("Mes").append(SEPARADOR)
            .append("Categoria").append(SEPARADOR)
            .append("Tipo").append(SEPARADOR)
            .append("Importe").append(SEPARADOR)
            .append("Descripcion")
            .append("\n")

        meses.forEach { mes ->
            val nombreMes = Formato.nombreMes(mes.mes, mes.anio)
            mes.categorias.forEach { categoria ->
                categoria.gastos.forEach { gasto ->
                    sb.append(Formato.fechaCorta(gasto.fecha)).append(SEPARADOR)
                    sb.append(nombreMes).append(SEPARADOR)
                    sb.append(categoria.tipo.etiqueta).append(SEPARADOR)
                    sb.append(if (gasto.esIngreso) "Ingreso" else if (gasto.esTraspasoSalida) "Traspaso (salida)" else "Gasto").append(SEPARADOR)
                    sb.append(Formato.importeEditable(gasto.importe)).append(SEPARADOR)
                    sb.append(escaparCampo(gasto.descripcion.orEmpty()))
                    sb.append("\n")
                }
            }
        }
        return sb.toString()
    }

    /** Envuelve el campo entre comillas si contiene el separador, una coma,
     *  salto de línea o comillas, y duplica las comillas internas (RFC 4180). */
    private fun escaparCampo(valor: String): String {
        val necesitaComillas = valor.contains(SEPARADOR) || valor.contains(',') ||
                valor.contains('\n') || valor.contains('"')
        return if (necesitaComillas) "\"" + valor.replace("\"", "\"\"") + "\"" else valor
    }
}