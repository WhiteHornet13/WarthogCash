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
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityMyMonthsBinding
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.ui.createmonth.CreateMonthActivity
import com.warthogcash.presupuesto.ui.main.MainActivity
import com.warthogcash.presupuesto.ui.monthdetail.MonthDetailActivity
import com.warthogcash.presupuesto.util.FabricaViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import androidx.appcompat.app.AlertDialog
import com.warthogcash.presupuesto.domain.model.EstadoPresupuesto
import com.warthogcash.presupuesto.util.Formato

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

        adapter = MonthListAdapter(
            alPulsarMes = ::navegarDesdeMes,
            alMantenerPulsadoMes = ::mostrarOpcionesMes
        )
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

        binding.btnFunciones.setOnClickListener { mostrarHojaFunciones() }

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

    private fun mostrarHojaFunciones() {
        val hoja = com.google.android.material.bottomsheet.BottomSheetDialog(this)
        val vista = layoutInflater.inflate(R.layout.bottom_sheet_funciones, null)
        hoja.setContentView(vista)

        vista.findViewById<android.view.View>(R.id.opcionGraficas).setOnClickListener {
            hoja.dismiss()
            startActivity(Intent(this, com.warthogcash.presupuesto.ui.graficas.GraficasActivity::class.java))
        }
        vista.findViewById<android.view.View>(R.id.opcionExportar).setOnClickListener {
            hoja.dismiss()
            startActivity(Intent(this, com.warthogcash.presupuesto.ui.export.ExportActivity::class.java))
        }
        vista.findViewById<android.view.View>(R.id.opcionResumenAnual).setOnClickListener {
            hoja.dismiss()
            // TODO Parte siguiente: abrir pantalla de Resumen anual
        }

        hoja.show()
    }

    private fun mostrarOpcionesMes(mes: Presupuesto) {
        val opciones = if (mes.estado == EstadoPresupuesto.ABIERTO) {
            arrayOf(
                getString(R.string.mis_meses_opcion_editar_dinero),
                getString(R.string.mis_meses_opcion_eliminar)
            )
        } else {
            arrayOf(getString(R.string.mis_meses_opcion_eliminar))
        }

        AlertDialog.Builder(this, R.style.ThemeOverlay_WarthogCash_Dialog)
            .setTitle(mes.nombreMesAnio)
            .setItems(opciones) { _, indice ->
                when (opciones[indice]) {
                    getString(R.string.mis_meses_opcion_editar_dinero) -> mostrarDialogoEditarDinero(mes)
                    getString(R.string.mis_meses_opcion_eliminar) -> confirmarEliminarMes(mes)
                }
            }
            .show()
    }

    private fun confirmarEliminarMes(mes: Presupuesto) {
        AlertDialog.Builder(this, R.style.ThemeOverlay_WarthogCash_Dialog)
            .setTitle(R.string.mis_meses_eliminar_confirmar_titulo)
            .setMessage(getString(R.string.mis_meses_eliminar_confirmar_mensaje_formato, mes.nombreMesAnio))
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.accion_eliminar) { _, _ ->
                viewModel.eliminarMes(mes.id)
            }
            .show()
    }

    private fun mostrarDialogoEditarDinero(mes: Presupuesto) {
        val margen = (16 * resources.displayMetrics.density).toInt()
        val etDinero = android.widget.EditText(this).apply {
            setText(Formato.importeEditable(mes.dineroDisponible))
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            keyListener = android.text.method.DigitsKeyListener.getInstance("0123456789,.")
            setTextColor(androidx.core.content.ContextCompat.getColor(context, R.color.texto_principal))
            setBackgroundResource(R.drawable.bg_card_borde_suave)
            setPadding(margen, margen / 2, margen, margen / 2)
        }

        AlertDialog.Builder(this, R.style.ThemeOverlay_WarthogCash_Dialog)
            .setTitle(R.string.mis_meses_editar_dinero_titulo)
            .setView(etDinero)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.historial_editar_boton_guardar) { _, _ ->
                val nuevoValor = etDinero.text.toString().replace(',', '.').toDoubleOrNull()
                if (nuevoValor == null || nuevoValor <= 0.0) return@setPositiveButton
                viewModel.actualizarDineroDisponible(mes.id, nuevoValor)
            }
            .show()
    }
}
