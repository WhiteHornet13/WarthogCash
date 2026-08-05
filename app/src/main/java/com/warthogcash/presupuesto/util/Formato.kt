package com.warthogcash.presupuesto.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

/** Formateo de importes y fechas coherente con los mockups (ej. "1.284,50 €"). */
object Formato {

    private val formatoMoneda: NumberFormat by lazy {
        NumberFormat.getCurrencyInstance(Locale("es", "ES"))
    }

    fun moneda(valor: Double): String = formatoMoneda.format(valor)

    /** Formato editable sin símbolo de moneda ni separador de miles, para
     * precargar EditText que luego vuelven a parsearse (ej. "1284,50"),
     * evitando que el punto de "1.284,50 €" se confunda con el decimal. */
    fun importeEditable(valor: Double): String =
        String.format(Locale("es", "ES"), "%.2f", valor)

    private val nombresMes = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    /** "Julio 2026" a partir de mes (1-12) y año. */
    fun nombreMes(mes: Int, anio: Int): String = "${nombresMes[mes - 1]} $anio"

    fun soloNombreMes(mes: Int): String = nombresMes[mes - 1]

    private val formatoFechaCorta = SimpleDateFormat("d MMM", Locale("es", "ES"))

    fun fechaCorta(epochMillis: Long): String = formatoFechaCorta.format(epochMillis)
}
