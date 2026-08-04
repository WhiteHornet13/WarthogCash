package com.warthogcash.presupuesto.ui.mymonths

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.databinding.ActivityMyMonthsBinding
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.ui.createmonth.CreateMonthActivity
import com.warthogcash.presupuesto.ui.main.MainActivity
import com.warthogcash.presupuesto.ui.monthdetail.MonthDetailActivity
import com.warthogcash.presupuesto.util.FabricaViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Especificación de pantalla "Mis meses". Se accede desde el icono de
 * calendario del header de la Pantalla principal (sección 2).
 */
class MyMonthsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyMonthsBinding
    private lateinit var adapter: MonthListAdapter

    private val viewModel: MyMonthsViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(this, FabricaViewModel { MyMonthsViewModel(repo) })[MyMonthsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMonthsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }

        adapter = MonthListAdapter(alPulsarMes = ::navegarDesdeMes)
        binding.listaMeses.layoutManager = LinearLayoutManager(this)
        binding.listaMeses.adapter = adapter

        // 4.2: la carga de la siguiente tanda se dispara por proximidad
        // del usuario al final del scroll.
        binding.listaMeses.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lm = recyclerView.layoutManager as LinearLayoutManager
                val ultimoVisible = lm.findLastVisibleItemPosition()
                val total = lm.itemCount
                if (dy > 0 && ultimoVisible >= total - 4) {
                    viewModel.cargarSiguientePagina()
                }
            }
        })

        binding.btnAjustes.setOnClickListener {
            startActivity(Intent(this, com.warthogcash.presupuesto.ui.settings.SettingsActivity::class.java))
        }

        binding.fabNuevoMes.setOnClickListener {
            startActivity(Intent(this, CreateMonthActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(viewModel.meses, viewModel.cargando) { meses, cargando -> meses to cargando }
                    .collect { (meses, cargando) -> adapter.actualizar(meses, cargando) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.recargar()
    }

    // 4.4: navegación según el mes pulsado.
    private fun navegarDesdeMes(mes: Presupuesto) {
        if (mes.esActual) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(
                Intent(this, MonthDetailActivity::class.java)
                    .putExtra(MonthDetailActivity.EXTRA_MES_ID, mes.id)
            )
        }
    }
}
