package com.warthogcash.presupuesto.ui.fixedexpenses

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivitySelectFixedExpensesBinding
import com.warthogcash.presupuesto.databinding.ItemFixedExpenseSelectableBinding
import com.warthogcash.presupuesto.ui.main.MainActivity
import com.warthogcash.presupuesto.util.FabricaViewModel
import com.warthogcash.presupuesto.util.Formato
import kotlinx.coroutines.launch

/**
 * Pantalla "Seleccionar gastos fijos". Se abre justo después de crear
 * un mes nuevo, solo si existe al menos un gasto fijo definido (ver
 * CreateMonthActivity). Permite elegir qué gastos fijos se aplican a
 * ese mes y ajustar su coste solo para ese mes, sin modificar el gasto
 * fijo original.
 */
class SelectFixedExpensesActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MES_ID = "extra_mes_id"
    }

    private lateinit var binding: ActivitySelectFixedExpensesBinding
    private var mesId: Long = -1
    private val filasPorId = mutableMapOf<Long, ItemFixedExpenseSelectableBinding>()

    private val viewModel: SelectFixedExpensesViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(
            this,
            FabricaViewModel { SelectFixedExpensesViewModel(repo, mesId) }
        )[SelectFixedExpensesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mesId = intent.getLongExtra(EXTRA_MES_ID, -1)

        binding = ActivitySelectFixedExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val repo = (application as App).repository
            val mes = repo.obtenerMesPorId(mesId)
            binding.tvTituloHeader.text = getString(
                R.string.seleccionar_gastos_fijos_titulo_formato, mes?.nombreMesAnio.orEmpty()
            )
        }

        binding.btnOmitir.setOnClickListener { irAPrincipal() }
        binding.btnAnadirAlMes.setOnClickListener { confirmarYContinuar() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { items -> pintarItems(items) }
            }
        }
    }

    private fun pintarItems(items: List<GastoFijoSeleccionable>) {
        if (binding.contenedorItems.childCount == 0) {
            items.forEach { item -> crearFila(item) }
        }
        actualizarResumen(items)
    }

    private fun crearFila(itemInicial: GastoFijoSeleccionable) {
        val filaBinding = ItemFixedExpenseSelectableBinding.inflate(
            layoutInflater, binding.contenedorItems, false
        )
        val id = itemInicial.gastoFijo.id
        filasPorId[id] = filaBinding

        filaBinding.tvComentarioGastoFijo.text = itemInicial.gastoFijo.comentario
            ?.takeIf { it.isNotBlank() } ?: itemInicial.gastoFijo.tipo.etiqueta
        filaBinding.tvBadgeCategoria.text = itemInicial.gastoFijo.tipo.etiqueta
        filaBinding.tvBadgeCategoria.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(this, itemInicial.gastoFijo.tipo.colorResId)
        )
        filaBinding.etCosteEsteMes.setText(Formato.importeEditable(itemInicial.costeDeEsteMes))
        filaBinding.checkSeleccionado.isChecked = itemInicial.seleccionado

        filaBinding.checkSeleccionado.setOnCheckedChangeListener { _, _ ->
            viewModel.alternarSeleccion(id)
        }
        filaBinding.etCosteEsteMes.setOnFocusChangeListener { _, tieneFoco ->
            if (!tieneFoco) {
                val nuevoCoste = filaBinding.etCosteEsteMes.text.toString()
                    .replace(',', '.').toDoubleOrNull()
                if (nuevoCoste != null) viewModel.actualizarCoste(id, nuevoCoste)
            }
        }

        binding.contenedorItems.addView(filaBinding.root)
    }

    private fun actualizarResumen(items: List<GastoFijoSeleccionable>) {
        items.forEach { item ->
            filasPorId[item.gastoFijo.id]?.root?.alpha = if (item.seleccionado) 1f else 0.45f
            filasPorId[item.gastoFijo.id]?.etCosteEsteMes?.isEnabled = item.seleccionado
        }
        val seleccionados = items.filter { it.seleccionado }
        binding.tvTotalSeleccionado.text = Formato.moneda(seleccionados.sumOf { it.costeDeEsteMes })
        binding.btnAnadirAlMes.text = getString(
            R.string.seleccionar_gastos_fijos_boton_formato, seleccionados.size
        )
    }

    private fun confirmarYContinuar() {
        binding.btnAnadirAlMes.isEnabled = false
        lifecycleScope.launch {
            viewModel.confirmar()
            irAPrincipal()
        }
    }

    private fun irAPrincipal() {
        val intent = Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}