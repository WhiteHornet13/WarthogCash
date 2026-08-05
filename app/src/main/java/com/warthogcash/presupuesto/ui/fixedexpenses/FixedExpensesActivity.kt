package com.warthogcash.presupuesto.ui.fixedexpenses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityFixedExpensesBinding
import com.warthogcash.presupuesto.domain.model.GastoFijo
import com.warthogcash.presupuesto.util.FabricaViewModel
import kotlinx.coroutines.launch

/**
 * Pantalla "Gastos fijos" (gestión CRUD), accesible desde Ajustes.
 * Plantillas reutilizables ofrecidas al crear cada mes (ver
 * "Seleccionar gastos fijos" en el flujo de "Crear mes nuevo").
 */
class FixedExpensesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFixedExpensesBinding
    private lateinit var adapter: FixedExpenseAdapter

    private val viewModel: FixedExpensesViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(this, FabricaViewModel { FixedExpensesViewModel(repo) })[FixedExpensesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFixedExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }

        adapter = FixedExpenseAdapter(
            alPulsarEditar = ::abrirFormularioEdicion,
            alPulsarEliminar = ::confirmarEliminacion
        )
        binding.listaGastosFijos.layoutManager = LinearLayoutManager(this)
        binding.listaGastosFijos.adapter = adapter

        binding.fabNuevoGastoFijo.setOnClickListener {
            startActivity(Intent(this, FixedExpenseFormActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gastosFijos.collect { lista ->
                    adapter.actualizar(lista)
                    binding.tvVacio.visibility = if (lista.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
                }
            }
        }
    }

    private fun abrirFormularioEdicion(gastoFijo: GastoFijo) {
        startActivity(
            Intent(this, FixedExpenseFormActivity::class.java)
                .putExtra(FixedExpenseFormActivity.EXTRA_GASTO_FIJO_ID, gastoFijo.id)
        )
    }

    private fun confirmarEliminacion(gastoFijo: GastoFijo) {
        AlertDialog.Builder(this)
            .setTitle(R.string.gastos_fijos_confirmar_eliminar_titulo)
            .setMessage(R.string.gastos_fijos_confirmar_eliminar_mensaje)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.accion_eliminar) { _, _ -> viewModel.eliminar(gastoFijo.id) }
            .show()
    }
}