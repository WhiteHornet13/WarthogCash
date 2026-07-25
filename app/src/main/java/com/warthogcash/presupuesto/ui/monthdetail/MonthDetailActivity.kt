package com.warthogcash.presupuesto.ui.monthdetail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.databinding.ActivityMonthDetailBinding
import com.warthogcash.presupuesto.domain.model.Categoria
import com.warthogcash.presupuesto.domain.model.EstadoPresupuesto
import com.warthogcash.presupuesto.ui.addexpense.AddExpenseActivity
import com.warthogcash.presupuesto.ui.closemonth.CloseMonthActivity
import com.warthogcash.presupuesto.ui.common.CategoriaAdapter
import com.warthogcash.presupuesto.ui.history.ExpenseHistoryActivity
import kotlinx.coroutines.launch

/**
 * Pantalla unificada que cubre tanto "Detalle de mes anterior (abierto)"
 * como "Detalle de mes cerrado": ambas especificaciones comparten la
 * misma estructura (header + lista de categorías) y solo difieren en los
 * bloques de acción disponibles, así que se implementan como una única
 * Activity que alterna su UI según el estado del mes cargado.
 */
class MonthDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MES_ID = "extra_mes_id"
    }

    private lateinit var binding: ActivityMonthDetailBinding
    private lateinit var adapter: CategoriaAdapter
    private var mesId: Long = -1

    private val viewModel: MonthDetailViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(
            this,
            com.warthogcash.presupuesto.util.FabricaViewModel { MonthDetailViewModel(repo, mesId) }
        )[MonthDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mesId = intent.getLongExtra(EXTRA_MES_ID, -1)

        binding = ActivityMonthDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CategoriaAdapter(alPulsarCategoria = ::abrirHistorialDeCategoria)
        binding.listaCategorias.adapter = adapter

        binding.fabAnadirGasto.setOnClickListener {
            startActivity(
                Intent(this, AddExpenseActivity::class.java)
                    .putExtra(AddExpenseActivity.EXTRA_MES_ID, mesId)
            )
        }

        binding.btnCerrarMes.setOnClickListener {
            startActivity(
                Intent(this, CloseMonthActivity::class.java)
                    .putExtra(CloseMonthActivity.EXTRA_MES_ID, mesId)
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mes.collect { mes ->
                    if (mes == null) return@collect
                    binding.headerResumen.mostrarComoDetalle(mes) { finish() }
                    adapter.actualizar(mes.categorias)

                    val esCerrado = mes.estado == EstadoPresupuesto.CERRADO
                    binding.bannerMesCerrado.visibility = if (esCerrado) android.view.View.VISIBLE else android.view.View.GONE
                    if (esCerrado) {
                        binding.bannerMesCerrado.setMensaje(getString(com.warthogcash.presupuesto.R.string.detalle_banner_cerrado))
                    }
                    // 4.2 (abierto) / 4.2 (cerrado): el botón "+" solo existe en meses abiertos.
                    binding.fabAnadirGasto.visibility = if (esCerrado) android.view.View.GONE else android.view.View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mostrarBotonCerrar.collect { mostrar ->
                    val esAbierto = viewModel.mes.value?.estado == EstadoPresupuesto.ABIERTO
                    binding.btnCerrarMes.visibility =
                        if (mostrar && esAbierto) android.view.View.VISIBLE else android.view.View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Al volver de "Añadir gasto" o de "Cerrar mes" refrescamos los datos.
        viewModel.recargar()
    }

    private fun abrirHistorialDeCategoria(categoria: Categoria) {
        startActivity(
            Intent(this, ExpenseHistoryActivity::class.java)
                .putExtra(ExpenseHistoryActivity.EXTRA_MES_ID, mesId)
                .putExtra(ExpenseHistoryActivity.EXTRA_TIPO_CATEGORIA, categoria.tipo.name)
        )
    }
}
