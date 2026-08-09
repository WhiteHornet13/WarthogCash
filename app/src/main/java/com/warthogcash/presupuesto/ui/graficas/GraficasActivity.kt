package com.warthogcash.presupuesto.ui.graficas

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityGraficasBinding
import com.warthogcash.presupuesto.databinding.ItemGraficaCardBinding
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.util.EstadisticasCalculator
import com.warthogcash.presupuesto.util.FabricaViewModel
import kotlinx.coroutines.launch

/**
 * Especificación acordada en chat: 5 tipos de gráfica, todas basadas
 * solo en meses CERRADOS. Ver EstadisticasCalculator para el cálculo
 * de cada una.
 */
class GraficasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraficasBinding
    private var anios: List<Int> = emptyList()

    private val viewModel: GraficasViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(this, FabricaViewModel { GraficasViewModel(repo) })[GraficasViewModel::class.java]
    }

    // Colores para hasta 3 series superpuestas (tipo 4): año base + 2 más.
    private val coloresSeries = listOf(R.color.verde_principal, R.color.azul_ocio, R.color.acento_ambar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraficasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }

        binding.chipsTipoGrafica.setOnCheckedStateChangeListener { _, _ -> actualizarControlesVisibles() }
        binding.btnGenerar.setOnClickListener { generarGraficas() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.aniosDisponibles.collect { lista ->
                    anios = lista
                    poblarSelectoresAnio()
                }
            }
        }

        val nombresMes = Presupuesto.NOMBRES_MES
        binding.spinnerMes.adapter = ArrayAdapter(this, R.layout.item_spinner_text, nombresMes).apply {
            setDropDownViewResource(R.layout.item_spinner_text)
        }

        actualizarControlesVisibles()
    }

    private fun poblarSelectoresAnio() {
        binding.spinnerAnioUnico.adapter = ArrayAdapter(
            this, R.layout.item_spinner_text, anios.map { it.toString() }
        ).apply { setDropDownViewResource(R.layout.item_spinner_text) }
        if (anios.isNotEmpty()) binding.spinnerAnioUnico.setSelection(anios.size - 1)

        binding.chipsAnios.removeAllViews()
        anios.forEach { anio ->
            val chip = com.google.android.material.chip.Chip(this).apply {
                text = anio.toString()
                isCheckable = true
                isClickable = true
                chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.chip_background_selector)
                setTextColor(ContextCompat.getColorStateList(context, R.color.chip_text_selector))
                isChecked = (anio == anios.last()) // año más reciente marcado por defecto
            }
            binding.chipsAnios.addView(chip)
        }
    }

    /** Muestra/oculta cada bloque de controles según el tipo de gráfica elegido
     *  (ver mapeo acordado: tipo1→año único, tipo2→mes+años, tipo3→año único,
     *  tipo4→años, tipo5→años). */
    private fun actualizarControlesVisibles() {
        val tipo = tipoSeleccionado()
        binding.contenedorAnioUnico.visibility = if (tipo == 1 || tipo == 3) android.view.View.VISIBLE else android.view.View.GONE
        binding.contenedorMes.visibility = if (tipo == 2) android.view.View.VISIBLE else android.view.View.GONE
        binding.contenedorAniosMultiples.visibility = if (tipo == 2 || tipo == 4 || tipo == 5) android.view.View.VISIBLE else android.view.View.GONE
        binding.contenedorGraficas.removeAllViews()
        binding.tvSinDatos.visibility = android.view.View.GONE
    }

    private fun tipoSeleccionado(): Int = when (binding.chipsTipoGrafica.checkedChipId) {
        R.id.chipTipo1 -> 1
        R.id.chipTipo2 -> 2
        R.id.chipTipo3 -> 3
        R.id.chipTipo4 -> 4
        R.id.chipTipo5 -> 5
        else -> 1
    }

    private fun aniosMarcadosEnChips(): List<Int> {
        val seleccionados = mutableListOf<Int>()
        for (i in 0 until binding.chipsAnios.childCount) {
            val chip = binding.chipsAnios.getChildAt(i) as com.google.android.material.chip.Chip
            if (chip.isChecked) seleccionados.add(chip.text.toString().toInt())
        }
        return seleccionados.sorted()
    }

    private fun generarGraficas() {
        binding.contenedorGraficas.removeAllViews()
        val meses = viewModel.meses.value

        when (tipoSeleccionado()) {
            1 -> {
                val anio = anios.getOrNull(binding.spinnerAnioUnico.selectedItemPosition) ?: return sinDatos()
                val datos = EstadisticasCalculator.gastoPorCategoriaMensual(meses, anio)
                val asignados = EstadisticasCalculator.asignadoPorCategoriaMensual(meses, anio)
                if (datos.values.all { it.all { v -> v == 0f } }) return sinDatos()
                TipoCategoria.ORDEN_VISUAL.forEach { tipo ->
                    agregarGraficaBarrasMeses(tipo.etiqueta, datos.getValue(tipo), tipo.colorResId, asignados.getValue(tipo))
                }
            }
            2 -> {
                val mes = binding.spinnerMes.selectedItemPosition + 1
                val aniosElegidos = aniosMarcadosEnChips()
                if (aniosElegidos.isEmpty()) return sinDatos()
                val datos = EstadisticasCalculator.gastoCategoriaPorAnios(meses, mes, aniosElegidos)
                if (datos.values.all { mapa -> mapa.values.all { it == 0f } }) return sinDatos()
                TipoCategoria.ORDEN_VISUAL.forEach { tipo ->
                    agregarGraficaBarrasAnios(tipo.etiqueta, datos.getValue(tipo), tipo.colorResId)
                }
            }
            3 -> {
                val anio = anios.getOrNull(binding.spinnerAnioUnico.selectedItemPosition) ?: return sinDatos()
                val resumen = EstadisticasCalculator.resumenMensual(meses, anio)
                if (resumen.gasto.all { it == 0f } && resumen.ahorro.all { it == 0f } && resumen.ingreso.all { it == 0f }) return sinDatos()
                agregarGraficaLineaMeses(getString(R.string.graficas_gasto), resumen.gasto, R.color.rojo_limite)
                agregarGraficaLineaMeses(getString(R.string.graficas_ahorro), resumen.ahorro, R.color.verde_ahorro)
                agregarGraficaLineaMeses(getString(R.string.graficas_ingreso), resumen.ingreso, R.color.verde_principal)
            }
            4 -> {
                val aniosElegidos = aniosMarcadosEnChips()
                if (aniosElegidos.isEmpty()) return sinDatos()
                val resumenes = EstadisticasCalculator.resumenMensualPorAnios(meses, aniosElegidos)
                agregarGraficaLineaMesesMultiAnio(getString(R.string.graficas_gasto), aniosElegidos) { resumenes.getValue(it).gasto }
                agregarGraficaLineaMesesMultiAnio(getString(R.string.graficas_ahorro), aniosElegidos) { resumenes.getValue(it).ahorro }
                agregarGraficaLineaMesesMultiAnio(getString(R.string.graficas_ingreso), aniosElegidos) { resumenes.getValue(it).ingreso }
            }
            5 -> {
                val aniosElegidos = aniosMarcadosEnChips()
                if (aniosElegidos.isEmpty()) return sinDatos()
                val resumen = EstadisticasCalculator.resumenAnual(meses, aniosElegidos)
                agregarGraficaBarrasAnios(getString(R.string.graficas_gasto), resumen.gasto, R.color.rojo_limite)
                agregarGraficaBarrasAnios(getString(R.string.graficas_ahorro), resumen.ahorro, R.color.verde_ahorro)
                agregarGraficaBarrasAnios(getString(R.string.graficas_ingreso), resumen.ingreso, R.color.verde_principal)
            }
        }
    }

    private fun sinDatos() {
        binding.tvSinDatos.visibility = android.view.View.VISIBLE
    }

    // --- Constructores de tarjeta + gráfica ---------------------------------

    private fun nuevaTarjeta(titulo: String): ItemGraficaCardBinding {
        val tarjeta = ItemGraficaCardBinding.inflate(LayoutInflater.from(this), binding.contenedorGraficas, false)
        tarjeta.tvTituloGrafica.text = titulo
        binding.contenedorGraficas.addView(tarjeta.root)
        return tarjeta
    }

    private val nombresMesCortos = listOf("Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic")

    /** Gráfica 1: barras, una por mes, para una categoría en un año. */
    /** Gráfica 1: barras (gasto real) + línea superpuesta (monto asignado),
     *  una por mes, para una categoría en un año. */
    private fun agregarGraficaBarrasMeses(titulo: String, valores: FloatArray, colorResId: Int, asignado: FloatArray) {
        val tarjeta = nuevaTarjeta(titulo)
        val chart = CombinedChart(this)
        chart.drawOrder = arrayOf(CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE)
        tarjeta.contenedorChart.addView(chart)

        val entradasBarras = valores.mapIndexed { i, v -> BarEntry(i.toFloat(), v) }
        val datasetBarras = BarDataSet(entradasBarras, getString(R.string.graficas_gasto)).apply {
            color = ContextCompat.getColor(this@GraficasActivity, colorResId)
            setDrawValues(false)
        }
        val barData = BarData(datasetBarras)

        val entradasLinea = asignado.mapIndexed { i, v -> Entry(i.toFloat(), v) }
        val datasetLinea = LineDataSet(entradasLinea, getString(R.string.graficas_asignado)).apply {
            color = ContextCompat.getColor(this@GraficasActivity, R.color.rojo_limite)
            setCircleColor(ContextCompat.getColor(this@GraficasActivity, R.color.rojo_limite))
            lineWidth = 3.5f
            circleRadius = 4f
            setDrawValues(false)
        }

        val combinedData = CombinedData()
        combinedData.setData(barData)
        combinedData.setData(LineData(datasetLinea))
        chart.data = combinedData

        configurarEjeXCategorias(chart.xAxis, nombresMesCortos)
        aplicarEstiloBasico(chart)
        chart.legend.isEnabled = true
    }

    /** Gráfica 2 y 5: barras, una por año (para una categoría o para gasto/ahorro/ingreso). */
    private fun agregarGraficaBarrasAnios(titulo: String, valoresPorAnio: Map<Int, Float>, colorResId: Int) {
        val tarjeta = nuevaTarjeta(titulo)
        val chart = BarChart(this)
        tarjeta.contenedorChart.addView(chart)

        val aniosOrdenados = valoresPorAnio.keys.sorted()
        val entradas = aniosOrdenados.mapIndexed { i, anio -> BarEntry(i.toFloat(), valoresPorAnio.getValue(anio)) }
        val dataset = BarDataSet(entradas, titulo).apply {
            color = ContextCompat.getColor(this@GraficasActivity, colorResId)
            setDrawValues(false)
        }
        chart.data = BarData(dataset)
        configurarEjeXCategorias(chart.xAxis, aniosOrdenados.map { it.toString() })
        aplicarEstiloBasico(chart)
    }

    /** Gráfica 3: línea única, 12 puntos (un mes). */
    private fun agregarGraficaLineaMeses(titulo: String, valores: FloatArray, colorResId: Int) {
        val tarjeta = nuevaTarjeta(titulo)
        val chart = LineChart(this)
        tarjeta.contenedorChart.addView(chart)

        val entradas = valores.mapIndexed { i, v -> Entry(i.toFloat(), v) }
        val dataset = LineDataSet(entradas, titulo).apply {
            color = ContextCompat.getColor(this@GraficasActivity, colorResId)
            setCircleColor(ContextCompat.getColor(this@GraficasActivity, colorResId))
            lineWidth = 2f
            circleRadius = 3f
            setDrawValues(false)
        }
        chart.data = LineData(dataset)
        configurarEjeXCategorias(chart.xAxis, nombresMesCortos)
        aplicarEstiloBasico(chart)
        chart.legend.isEnabled = false
    }

    /** Gráfica 4: varias líneas (una por año) sobre los 12 meses. */
    private fun agregarGraficaLineaMesesMultiAnio(titulo: String, aniosElegidos: List<Int>, extraerSerie: (Int) -> FloatArray) {
        val tarjeta = nuevaTarjeta(titulo)
        val chart = LineChart(this)
        tarjeta.contenedorChart.addView(chart)

        val datasets = aniosElegidos.mapIndexed { indice, anio ->
            val valores = extraerSerie(anio)
            val entradas = valores.mapIndexed { i, v -> Entry(i.toFloat(), v) }
            val color = ContextCompat.getColor(this, coloresSeries[indice % coloresSeries.size])
            LineDataSet(entradas, anio.toString()).apply {
                this.color = color
                setCircleColor(color)
                lineWidth = 2f
                circleRadius = 3f
                setDrawValues(false)
            }
        }
        chart.data = LineData(datasets)
        configurarEjeXCategorias(chart.xAxis, nombresMesCortos)
        aplicarEstiloBasico(chart)
        chart.legend.isEnabled = true
    }

    private fun configurarEjeXCategorias(eje: XAxis, etiquetas: List<String>) {
        eje.valueFormatter = IndexAxisValueFormatter(etiquetas)
        eje.position = XAxis.XAxisPosition.BOTTOM
        eje.granularity = 1f
        eje.setDrawGridLines(false)
        eje.textColor = ContextCompat.getColor(this, R.color.texto_secundario)
    }

    private fun aplicarEstiloBasico(chart: com.github.mikephil.charting.charts.Chart<*>) {
        chart.description.isEnabled = false
        chart.setNoDataText("")
        chart.legend.textColor = ContextCompat.getColor(this, R.color.texto_secundario)
        chart.axisLeftOrNull()?.textColor = ContextCompat.getColor(this, R.color.texto_secundario)
        chart.invalidate()
    }

    // Pequeño helper porque BarLineChartBase no expone directamente axisLeft
    // en el tipo genérico Chart<*>; evita repetir el cast en cada función.
    private fun com.github.mikephil.charting.charts.Chart<*>.axisLeftOrNull() =
        (this as? com.github.mikephil.charting.charts.BarLineChartBase<*>)?.axisLeft
}