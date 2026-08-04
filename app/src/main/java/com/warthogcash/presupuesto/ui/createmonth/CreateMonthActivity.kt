package com.warthogcash.presupuesto.ui.createmonth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityCreateMonthBinding
import com.warthogcash.presupuesto.databinding.ItemCategoryPercentInputBinding
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.ui.main.MainActivity
import com.warthogcash.presupuesto.util.FabricaViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import com.warthogcash.presupuesto.util.PorcentajesPredefinidos

/**
 * Especificación de pantalla "Crear mes nuevo". Se accede desde el botón
 * "Empezar" de Bienvenida (primer uso) o desde "+ Nuevo mes" en Mis meses.
 */
class CreateMonthActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ES_PRIMERA_VEZ = "extra_es_primera_vez"
    }

    private lateinit var binding: ActivityCreateMonthBinding

    // true si se llega desde "Bienvenida" (primer uso, sin ningún mes creado).
    private val esPrimeraVez: Boolean by lazy {
        intent.getBooleanExtra(EXTRA_ES_PRIMERA_VEZ, false)
    }


    private val viewModel: CreateMonthViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(this, FabricaViewModel { CreateMonthViewModel(repo) })[CreateMonthViewModel::class.java]
    }

    // Filas de porcentaje en el orden visual fijo de las 5 categorías.
    private lateinit var filas: Map<TipoCategoria, ItemCategoryPercentInputBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }

        binding.etDineroDisponible.setOnEditorActionListener { _, actionId, event ->
            val esEnter = actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE ||
                    (event != null &&
                            event.action == android.view.KeyEvent.ACTION_DOWN &&
                            event.keyCode == android.view.KeyEvent.KEYCODE_ENTER)
            if (esEnter) {
                ocultarTeclado(binding.etDineroDisponible)
                binding.etDineroDisponible.clearFocus()
                true
            } else {
                false
            }
        }

        configurarSelectoresMesAnio()
        configurarFilasCategoria()

        binding.btnCrearMes.setOnClickListener { intentarCrearMes() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mesAnteriorAbierto.collect { mes -> actualizarBannerAviso(mes) }
            }
        }
    }

    private fun configurarSelectoresMesAnio() {
        val nombresMes = Presupuesto.NOMBRES_MES
        val anioActual = Calendar.getInstance().get(Calendar.YEAR)
        val mesActual = Calendar.getInstance().get(Calendar.MONTH) // 0-indexado

        if (esPrimeraVez) {
            // Primer uso de la app (sin ningún mes creado todavía): el único
            // punto de partida con sentido es el mes actual del calendario del
            // dispositivo, para evitar que quede un mes creado que nunca se
            // marca como "actual" y deja la app sin ningún mes navegable.
            binding.spinnerMes.adapter = ArrayAdapter(
                this, R.layout.item_spinner_text, listOf(nombresMes[mesActual])
            ).apply { setDropDownViewResource(R.layout.item_spinner_text) }

            binding.spinnerAnio.adapter = ArrayAdapter(
                this, R.layout.item_spinner_text, listOf(anioActual.toString())
            ).apply { setDropDownViewResource(R.layout.item_spinner_text) }

            binding.spinnerMes.setSelection(0)
            binding.spinnerAnio.setSelection(0)
            binding.spinnerMes.isEnabled = false
            binding.spinnerAnio.isEnabled = false
            return
        }

        binding.spinnerMes.adapter = ArrayAdapter(this, R.layout.item_spinner_text, nombresMes).apply {
            setDropDownViewResource(R.layout.item_spinner_text)
        }

        val anios = (anioActual - 5..anioActual + 5).map { it.toString() }
        binding.spinnerAnio.adapter = ArrayAdapter(this, R.layout.item_spinner_text, anios).apply {
            setDropDownViewResource(R.layout.item_spinner_text)
        }

        // Selección por defecto: mes/año actuales (el usuario puede cambiarlos
        // libremente en este flujo; la selección sigue siendo 100% manual).
        binding.spinnerMes.setSelection(mesActual)
        binding.spinnerAnio.setSelection(5)
    }

    private fun configurarFilasCategoria() {
        filas = mapOf(
            TipoCategoria.GENERAL to binding.filaGeneral,
            TipoCategoria.AHORRO to binding.filaAhorro,
            TipoCategoria.INVERSION to binding.filaInversion,
            TipoCategoria.OCIO to binding.filaOcio,
            TipoCategoria.CULTURA to binding.filaCultura
        )

        val predefinidos = PorcentajesPredefinidos(this)
        filas.forEach { (tipo, filaBinding) ->
            filaBinding.tvNombreCategoria.text = tipo.etiqueta
            val valor = predefinidos.obtener(tipo)
            if (valor > 0.0) {
                filaBinding.etPorcentaje.setText(formatearSuma(valor))
            }
        }
    }

    private fun ocultarTeclado(vista: android.view.View) {
        val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(vista.windowToken, 0)
    }

    private fun actualizarBannerAviso(mesAnterior: Presupuesto?) {
        if (mesAnterior == null) {
            binding.bannerMesAnteriorAbierto.visibility = android.view.View.GONE
            return
        }
        binding.bannerMesAnteriorAbierto.visibility = android.view.View.VISIBLE
        binding.bannerMesAnteriorAbierto.setMensaje(
            getString(R.string.crear_mes_aviso_mes_abierto) + " (${mesAnterior.nombreMesAnio})"
        )
    }

    private fun intentarCrearMes() {
        val dinero = binding.etDineroDisponible.text.toString().replace(',', '.').toDoubleOrNull()
        if (dinero == null || dinero <= 0.0) {
            binding.etDineroDisponible.error = getString(R.string.crear_mes_error_dinero)
            return
        }

        val porcentajes = filas.mapValues { (_, fila) ->
            fila.etPorcentaje.text.toString().toDoubleOrNull() ?: 0.0
        }
        val suma = porcentajes.values.sum()

        if (Math.abs(suma - 100.0) > 0.01) {
            binding.tvErrorPorcentaje.visibility = android.view.View.VISIBLE
            binding.tvErrorPorcentaje.text = getString(
                R.string.crear_mes_error_porcentaje,
                formatearSuma(suma)
            )
            return
        }
        binding.tvErrorPorcentaje.visibility = android.view.View.GONE

        // Leer el mes por el TEXTO seleccionado (igual que el año), no por la
        // posición dentro del adapter: en el flujo "primera vez" el adapter de
        // spinnerMes solo contiene un elemento (el mes actual), así que su
        // posición siempre es 0 y calcular "posición + 1" daba un mes incorrecto.
        val mesSeleccionado = Presupuesto.NOMBRES_MES.indexOf(binding.spinnerMes.selectedItem.toString()) + 1
        val anioSeleccionado = binding.spinnerAnio.selectedItem.toString().toInt()

        binding.btnCrearMes.isEnabled = false
        lifecycleScope.launch {
            viewModel.crearMes(mesSeleccionado, anioSeleccionado, dinero, porcentajes)
            val intent = Intent(this@CreateMonthActivity, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }


    private fun formatearSuma(valor: Double): String =
        if (valor == valor.toLong().toDouble()) valor.toLong().toString() else valor.toString()
}
