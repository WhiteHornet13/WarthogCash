package com.warthogcash.presupuesto.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivitySettingsBinding
import com.warthogcash.presupuesto.databinding.ItemCategoryPercentInputBinding
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.util.PorcentajesPredefinidos

/**
 * Pantalla de opciones de la app. De momento solo contiene los
 * porcentajes predefinidos por categoría, usados como valor de partida
 * en "Crear mes nuevo"; está pensada para acoger más ajustes en el futuro.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var filas: Map<TipoCategoria, ItemCategoryPercentInputBinding>
    private lateinit var predefinidos: PorcentajesPredefinidos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        predefinidos = PorcentajesPredefinidos(this)

        binding.btnVolver.setOnClickListener { finish() }

        filas = mapOf(
            TipoCategoria.GENERAL to binding.filaGeneral,
            TipoCategoria.AHORRO to binding.filaAhorro,
            TipoCategoria.INVERSION to binding.filaInversion,
            TipoCategoria.OCIO to binding.filaOcio,
            TipoCategoria.CULTURA to binding.filaCultura
        )

        filas.forEach { (tipo, filaBinding) ->
            filaBinding.tvNombreCategoria.text = tipo.etiqueta
            val valor = predefinidos.obtener(tipo)
            if (valor > 0.0) {
                filaBinding.etPorcentaje.setText(formatearSuma(valor))
            }
        }

        binding.btnGuardarAjustes.setOnClickListener { guardarAjustes() }
    }

    private fun guardarAjustes() {
        val valores = filas.mapValues { (_, fila) ->
            fila.etPorcentaje.text.toString().toDoubleOrNull() ?: 0.0
        }
        val suma = valores.values.sum()

        // Igual que en "Crear mes nuevo": si el usuario rellena algo,
        // debe sumar 100%. Si lo deja todo a 0, se guarda vacío (sin
        // predefinidos) y no se aplica esta validación.
        if (suma > 0.0 && Math.abs(suma - 100.0) > 0.01) {
            binding.tvErrorPorcentaje.visibility = android.view.View.VISIBLE
            binding.tvErrorPorcentaje.text = getString(
                R.string.crear_mes_error_porcentaje,
                formatearSuma(suma)
            )
            return
        }
        binding.tvErrorPorcentaje.visibility = android.view.View.GONE

        predefinidos.guardar(valores)
        Toast.makeText(this, R.string.ajustes_guardado_confirmacion, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun formatearSuma(valor: Double): String =
        if (valor == valor.toLong().toDouble()) valor.toLong().toString() else valor.toString()
}