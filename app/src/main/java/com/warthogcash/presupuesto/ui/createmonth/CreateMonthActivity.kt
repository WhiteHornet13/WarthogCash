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

/**
 * Especificación de pantalla "Crear mes nuevo". Se accede desde el botón
 * "Empezar" de Bienvenida (primer uso) o desde "+ Nuevo mes" en Mis meses.
 */
class CreateMonthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateMonthBinding

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
        binding.spinnerMes.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombresMes)

        val anioActual = Calendar.getInstance().get(Calendar.YEAR)
        val anios = (anioActual - 5..anioActual + 5).map { it.toString() }
        binding.spinnerAnio.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, anios)

        // Selección por defecto: mes/año actuales (el usuario puede cambiarlos
        // libremente; la selección sigue siendo 100% manual, spec 4.1).
        binding.spinnerMes.setSelection(Calendar.getInstance().get(Calendar.MONTH))
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
        filas.forEach { (tipo, filaBinding) -> filaBinding.tvNombreCategoria.text = tipo.etiqueta }
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

        // 5. Fuera de alcance en la spec original: la validación exacta de
        // que sumen 100% (mensajes, redondeos) queda pendiente de definir.
        // Se aplica aquí la interpretación más directa: exigir suma = 100%.
        if (Math.abs(suma - 100.0) > 0.01) {
            binding.tvErrorPorcentaje.visibility = android.view.View.VISIBLE
            binding.tvErrorPorcentaje.text = getString(
                R.string.crear_mes_error_porcentaje,
                formatearSuma(suma)
            )
            return
        }
        binding.tvErrorPorcentaje.visibility = android.view.View.GONE

        val mesSeleccionado = binding.spinnerMes.selectedItemPosition + 1
        val anioSeleccionado = binding.spinnerAnio.selectedItem.toString().toInt()

        binding.btnCrearMes.isEnabled = false
        lifecycleScope.launch {
            viewModel.crearMes(mesSeleccionado, anioSeleccionado, dinero, porcentajes)
            // 4.5: navega automáticamente a la Pantalla principal con el
            // mes recién creado, sin dejar esta pantalla ni Bienvenida en el back stack.
            val intent = Intent(this@CreateMonthActivity, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun formatearSuma(valor: Double): String =
        if (valor == valor.toLong().toDouble()) valor.toLong().toString() else valor.toString()
}
