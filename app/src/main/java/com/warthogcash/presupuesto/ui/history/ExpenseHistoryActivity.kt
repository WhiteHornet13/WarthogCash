package com.warthogcash.presupuesto.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityExpenseHistoryBinding
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.util.FabricaViewModel
import kotlinx.coroutines.launch

/**
 * Especificación de pantalla "Historial de gastos". Pantalla genérica,
 * de solo consulta, sin botón "+" bajo ningún modo (sección 3.1/4.3).
 */
class ExpenseHistoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MES_ID = "extra_mes_id"
        /** Nombre del enum [TipoCategoria], o ausente para el modo genérico. */
        const val EXTRA_TIPO_CATEGORIA = "extra_tipo_categoria"
    }

    private lateinit var binding: ActivityExpenseHistoryBinding
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mesId = intent.getLongExtra(EXTRA_MES_ID, -1)
        val tipoFiltro = intent.getStringExtra(EXTRA_TIPO_CATEGORIA)?.let { TipoCategoria.valueOf(it) }

        val viewModel: ExpenseHistoryViewModel by lazy {
            val repo = (application as App).repository
            ViewModelProvider(
                this,
                FabricaViewModel { ExpenseHistoryViewModel(repo, mesId, tipoFiltro) }
            )[ExpenseHistoryViewModel::class.java]
        }

        binding = ActivityExpenseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }

        adapter = ExpenseAdapter()
        binding.listaGastos.layoutManager = LinearLayoutManager(this)
        binding.listaGastos.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mes.collect { mes ->
                    if (mes == null) return@collect
                    // 3.2: título dinámico según el modo.
                    binding.tvTituloHeader.text = if (tipoFiltro != null) {
                        getString(R.string.historial_titulo_filtrado_formato, tipoFiltro.etiqueta, mes.nombreMesAnio)
                    } else {
                        getString(R.string.historial_titulo_generico_formato, mes.nombreMesAnio)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gastos.collect { gastos ->
                    adapter.actualizar(gastos)
                    binding.tvVacio.visibility = if (gastos.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
                }
            }
        }
    }
}
