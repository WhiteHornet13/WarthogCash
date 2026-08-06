package com.warthogcash.presupuesto.ui.history

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityExpenseHistoryBinding
import com.warthogcash.presupuesto.domain.model.EstadoPresupuesto
import com.warthogcash.presupuesto.domain.model.GastoDetallado
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.util.FabricaViewModel
import com.warthogcash.presupuesto.util.Formato
import kotlinx.coroutines.launch

/**
 * Especificación de pantalla "Historial de gastos". Pantalla genérica,
 * de solo consulta salvo edición/eliminación de gastos individuales
 * (añadido en 1.6.0), disponibles únicamente si el mes está abierto.
 * Ambas acciones piden confirmación porque recalculan el gasto de la
 * categoría afectada.
 */
class ExpenseHistoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MES_ID = "extra_mes_id"
        const val EXTRA_TIPO_CATEGORIA = "extra_tipo_categoria"
    }

    private lateinit var binding: ActivityExpenseHistoryBinding
    private lateinit var adapter: ExpenseAdapter
    private lateinit var viewModel: ExpenseHistoryViewModel
    private var mesEditable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mesId = intent.getLongExtra(EXTRA_MES_ID, -1)
        val tipoFiltro = intent.getStringExtra(EXTRA_TIPO_CATEGORIA)?.let { TipoCategoria.valueOf(it) }

        viewModel = ViewModelProvider(
            this,
            FabricaViewModel { ExpenseHistoryViewModel((application as App).repository, mesId, tipoFiltro) }
        )[ExpenseHistoryViewModel::class.java]

        binding = ActivityExpenseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }

        adapter = ExpenseAdapter(
            alPulsarEditar = ::mostrarDialogoEditar,
            alPulsarEliminar = ::mostrarDialogoEliminar
        )
        binding.listaGastos.layoutManager = LinearLayoutManager(this)
        binding.listaGastos.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mes.collect { mes ->
                    if (mes == null) return@collect
                    mesEditable = mes.estado == EstadoPresupuesto.ABIERTO
                    binding.tvTituloHeader.text = if (tipoFiltro != null) {
                        getString(R.string.historial_titulo_filtrado_formato, tipoFiltro.etiqueta, mes.nombreMesAnio)
                    } else {
                        getString(R.string.historial_titulo_generico_formato, mes.nombreMesAnio)
                    }
                    adapter.actualizar(viewModel.gastos.value, mesEditable)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gastos.collect { gastos ->
                    adapter.actualizar(gastos, mesEditable)
                    binding.tvVacio.visibility = if (gastos.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
                }
            }
        }
    }

    private fun mostrarDialogoEliminar(item: GastoDetallado) {
        val descripcion = item.gasto.descripcion?.takeIf { it.isNotBlank() } ?: item.categoria.etiqueta
        AlertDialog.Builder(this, R.style.ThemeOverlay_WarthogCash_Dialog)
            .setTitle(R.string.historial_confirmar_eliminar_titulo)
            .setMessage(
                getString(
                    R.string.historial_confirmar_eliminar_mensaje_formato,
                    descripcion,
                    Formato.moneda(item.gasto.importe),
                    item.categoria.etiqueta
                )
            )
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.accion_eliminar) { _, _ ->
                lifecycleScope.launch { viewModel.eliminarGasto(item.gasto.id) }
            }
            .show()
    }

    private fun mostrarDialogoEditar(item: GastoDetallado) {
        val margen = (16 * resources.displayMetrics.density).toInt()
        val paddingCampo = (12 * resources.displayMetrics.density).toInt()

        val etImporte = EditText(this).apply {
            setText(Formato.importeEditable(item.gasto.importe))
            hint = getString(R.string.historial_editar_importe)
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            keyListener = android.text.method.DigitsKeyListener.getInstance("0123456789,.")
            setTextColor(androidx.core.content.ContextCompat.getColor(context, R.color.texto_principal))
            setHintTextColor(androidx.core.content.ContextCompat.getColor(context, R.color.texto_secundario))
            setBackgroundResource(R.drawable.bg_card_borde_suave)
            setPadding(paddingCampo, paddingCampo, paddingCampo, paddingCampo)
            addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
                override fun afterTextChanged(s: android.text.Editable?) {
                    if (s?.contains('.') == true) {
                        val posicion = selectionStart
                        s.replace(0, s.length, s.toString().replace('.', ','))
                        setSelection(posicion.coerceIn(0, s.length))
                    }
                }
            })
        }
        val etDescripcion = EditText(this).apply {
            setText(item.gasto.descripcion)
            hint = getString(R.string.historial_editar_descripcion)
            setTextColor(androidx.core.content.ContextCompat.getColor(context, R.color.texto_principal))
            setHintTextColor(androidx.core.content.ContextCompat.getColor(context, R.color.texto_secundario))
            setBackgroundResource(R.drawable.bg_card_borde_suave)
            setPadding(paddingCampo, paddingCampo, paddingCampo, paddingCampo)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = margen / 2 }
        }

        val contenedor = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(margen, margen / 2, margen, 0)
            addView(etImporte)
            addView(etDescripcion)
        }

        AlertDialog.Builder(this, R.style.ThemeOverlay_WarthogCash_Dialog)
            .setTitle(R.string.historial_confirmar_editar_titulo)
            .setView(contenedor)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.historial_editar_boton_guardar) { _, _ ->
                val nuevoImporte = etImporte.text.toString().replace(',', '.').toDoubleOrNull()
                if (nuevoImporte == null || nuevoImporte <= 0.0) return@setPositiveButton
                val nuevaDescripcion = etDescripcion.text.toString().trim().ifEmpty { null }
                lifecycleScope.launch { viewModel.editarGasto(item.gasto.id, nuevoImporte, nuevaDescripcion) }
            }
            .show()
    }
}