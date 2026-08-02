package com.warthogcash.presupuesto.ui.addexpense

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityAddExpenseBinding
import com.warthogcash.presupuesto.domain.model.Categoria
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.util.FabricaViewModel
import com.warthogcash.presupuesto.util.Formato
import kotlinx.coroutines.launch

/**
 * Especificación de pantalla "Añadir gasto". Solo accesible desde el
 * botón + de la Pantalla principal o del detalle de un mes anterior
 * abierto (sección 2); nunca desde un mes cerrado.
 */
class AddExpenseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MES_ID = "extra_mes_id"
    }

    private lateinit var binding: ActivityAddExpenseBinding
    private var mesId: Long = -1
    private var categoriaSeleccionada: Categoria? = null

    private val viewModel: AddExpenseViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(
            this,
            FabricaViewModel { AddExpenseViewModel(repo, mesId) }
        )[AddExpenseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mesId = intent.getLongExtra(EXTRA_MES_ID, -1)

        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }

        binding.etImporte.addTextChangedListener(alCambiarTexto = { actualizarVistaPreviaYBoton() })
        binding.etDescripcion.addTextChangedListener(alCambiarTexto = { })

        binding.btnGuardarGasto.setOnClickListener { guardarGasto() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mes.collect { mes -> if (mes != null) construirChips(mes) }
            }
        }
    }

    private fun construirChips(mes: Presupuesto) {
        if (binding.grupoChipsCategoria.childCount > 0) return // ya construidos
        TipoCategoria.ORDEN_VISUAL.forEach { tipo ->
            val categoria = mes.categorias.firstOrNull { it.tipo == tipo } ?: return@forEach
            val chip = Chip(this).apply {
                text = tipo.etiqueta
                isCheckable = true
                isClickable = true
                chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.chip_background_selector)
                setTextColor(ContextCompat.getColorStateList(context, R.color.chip_text_selector))
                chipStrokeColor = android.content.res.ColorStateList.valueOf(
                    ContextCompat.getColor(context, categoria.tipo.colorResId)
                )
                chipStrokeWidth = resources.getDisplayMetrics().density
                setOnClickListener {
                    categoriaSeleccionada = categoria
                    actualizarVistaPreviaYBoton()
                }
            }
            binding.grupoChipsCategoria.addView(chip)
        }
    }

    // Spec 4.4: al elegir categoría se muestra al instante el saldo
    // resultante en esa categoría antes de confirmar.
    private fun actualizarVistaPreviaYBoton() {
        val importe = importeIngresado()
        val categoria = categoriaSeleccionada

        if (categoria != null && importe != null) {
            val restanteResultante = categoria.restante - importe
            binding.contenedorVistaPrevia.visibility = android.view.View.VISIBLE
            binding.tvVistaPrevia.text = getString(
                R.string.anadir_gasto_vista_previa_formato,
                categoria.tipo.etiqueta,
                Formato.moneda(restanteResultante)
            )
        } else {
            binding.contenedorVistaPrevia.visibility = android.view.View.GONE
        }

        // 4.1/4.2/4.6: importe > 0 y categoría seleccionada son obligatorios.
        binding.btnGuardarGasto.isEnabled = categoria != null && importe != null && importe > 0.0
    }

    private fun importeIngresado(): Double? =
        binding.etImporte.text.toString().replace(',', '.').toDoubleOrNull()

    private fun guardarGasto() {
        val categoria = categoriaSeleccionada ?: return
        val importe = importeIngresado() ?: return
        if (importe <= 0.0) return

        val descripcion = binding.etDescripcion.text.toString().trim().ifEmpty { null }

        binding.btnGuardarGasto.isEnabled = false
        lifecycleScope.launch {
            viewModel.guardarGasto(categoria.id, importe, descripcion)
            // 4.6: confirmación exitosa -> vuelve automáticamente a la pantalla de origen.
            finish()
        }
    }
}

/** Pequeño helper para no repetir la implementación completa de TextWatcher. */
private fun android.widget.EditText.addTextChangedListener(alCambiarTexto: () -> Unit) {
    addTextChangedListener(object : android.text.TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = alCambiarTexto()
        override fun afterTextChanged(s: android.text.Editable?) = Unit
    })
}
