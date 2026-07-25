package com.warthogcash.presupuesto.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.databinding.ActivityMainBinding
import com.warthogcash.presupuesto.domain.model.Categoria
import com.warthogcash.presupuesto.ui.addexpense.AddExpenseActivity
import com.warthogcash.presupuesto.ui.common.CategoriaAdapter
import com.warthogcash.presupuesto.ui.history.ExpenseHistoryActivity
import com.warthogcash.presupuesto.ui.mymonths.MyMonthsActivity
import com.warthogcash.presupuesto.util.FabricaViewModel
import kotlinx.coroutines.launch

/**
 * Pantalla principal: muestra el estado del mes actual (especificación
 * de pantalla "Pantalla principal"). Es la pantalla de entrada habitual
 * de la app una vez existe al menos un mes creado.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CategoriaAdapter

    private val viewModel: MainViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(this, FabricaViewModel { MainViewModel(repo) })[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CategoriaAdapter(alPulsarCategoria = ::abrirHistorialDeCategoria)
        binding.listaCategorias.adapter = adapter

        binding.fabAnadirGasto.setOnClickListener {
            val mesId = viewModel.mesActual.value?.id ?: return@setOnClickListener
            startActivity(
                Intent(this, AddExpenseActivity::class.java)
                    .putExtra(AddExpenseActivity.EXTRA_MES_ID, mesId)
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mesActual.collect { mes ->
                    if (mes == null) return@collect
                    binding.headerResumen.mostrarComoPantallaPrincipal(mes) {
                        startActivity(Intent(this@MainActivity, MyMonthsActivity::class.java))
                    }
                    adapter.actualizar(mes.categorias)
                }
            }
        }
    }

    private fun abrirHistorialDeCategoria(categoria: Categoria) {
        val mesId = viewModel.mesActual.value?.id ?: return
        startActivity(
            Intent(this, ExpenseHistoryActivity::class.java)
                .putExtra(ExpenseHistoryActivity.EXTRA_MES_ID, mesId)
                .putExtra(ExpenseHistoryActivity.EXTRA_TIPO_CATEGORIA, categoria.tipo.name)
        )
    }
}
