package com.warthogcash.presupuesto.ui.fixedexpenses

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityFixedExpenseFormBinding
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.util.Formato
import kotlinx.coroutines.launch

/**
 * Formulario de alta/edición de un gasto fijo (Coste, Categoría,
 * Comentario). Si llega [EXTRA_GASTO_FIJO_ID] edita ese gasto fijo;
 * si no, crea uno nuevo.
 */
class FixedExpenseFormActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_GASTO_FIJO_ID = "extra_gasto_fijo_id"
    }

    private lateinit var binding: ActivityFixedExpenseFormBinding
    private var gastoFijoId: Long = -1
    private var tipoSeleccionado: TipoCategoria? = null
    private val repo by lazy { (application as App).repository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gastoFijoId = intent.getLongExtra(EXTRA_GASTO_FIJO_ID, -1)

        binding = ActivityFixedExpenseFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTituloHeader.text = getString(
            if (gastoFijoId == -1L) R.string.gastos_fijos_titulo_nuevo else R.string.gastos_fijos_titulo_editar
        )
        binding.btnVolver.setOnClickListener { finish() }

        construirChips()

        if (gastoFijoId != -1L) {
            lifecycleScope.launch {
                val gastoFijo = repo.obtenerGastosFijos().firstOrNull { it.id == gastoFijoId } ?: return@launch
                binding.etCoste.setText(Formato.importeEditable(gastoFijo.coste))
                binding.etComentario.setText(gastoFijo.comentario)
                seleccionarChip(gastoFijo.tipo)
            }
        }

        binding.btnGuardarGastoFijo.setOnClickListener { guardar() }
    }

    private fun construirChips() {
        TipoCategoria.ORDEN_VISUAL.forEach { tipo ->
            val chip = Chip(this).apply {
                text = tipo.etiqueta
                isCheckable = true
                isClickable = true
                chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.chip_background_selector)
                setTextColor(ContextCompat.getColorStateList(context, R.color.chip_text_selector))
                chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, tipo.colorResId))
                chipStrokeWidth = resources.displayMetrics.density
                setOnClickListener { tipoSeleccionado = tipo }
            }
            binding.grupoChipsCategoria.addView(chip)
        }
    }

    private fun seleccionarChip(tipo: TipoCategoria) {
        tipoSeleccionado = tipo
        val posicion = TipoCategoria.ORDEN_VISUAL.indexOf(tipo)
        (binding.grupoChipsCategoria.getChildAt(posicion) as? Chip)?.isChecked = true
    }

    private fun guardar() {
        val coste = binding.etCoste.text.toString().replace(',', '.').toDoubleOrNull()
        val tipo = tipoSeleccionado

        if (coste == null || coste <= 0.0) {
            binding.etCoste.error = getString(R.string.gastos_fijos_error_coste)
            return
        }
        if (tipo == null) {
            Toast.makeText(this, R.string.gastos_fijos_error_categoria, Toast.LENGTH_SHORT).show()
            return
        }
        val comentario = binding.etComentario.text.toString().trim().ifEmpty { null }

        binding.btnGuardarGastoFijo.isEnabled = false
        lifecycleScope.launch {
            if (gastoFijoId == -1L) {
                repo.crearGastoFijo(coste, tipo, comentario)
            } else {
                repo.actualizarGastoFijo(gastoFijoId, coste, tipo, comentario)
            }
            finish()
        }
    }
}