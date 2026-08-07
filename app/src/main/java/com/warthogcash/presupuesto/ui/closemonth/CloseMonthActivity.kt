package com.warthogcash.presupuesto.ui.closemonth

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityCloseMonthBinding
import com.warthogcash.presupuesto.databinding.ItemCategoryTransferSelectableBinding
import com.warthogcash.presupuesto.domain.model.Categoria
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.util.FabricaViewModel
import com.warthogcash.presupuesto.util.Formato
import kotlinx.coroutines.launch

/**
 * Regla confirmada: solo se puede elegir el traspaso al mes siguiente cuando
 * este mes es el inmediatamente anterior al actual. En cualquier otro mes
 * abierto ("resto de meses"), se cierra directo con todo el sobrante yendo
 * a Ahorro, sin mostrar la lista de categorías.
 */
class CloseMonthActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MES_ID = "extra_mes_id"
    }

    private lateinit var binding: ActivityCloseMonthBinding
    private var mesId: Long = -1

    private val filasPorCategoria = mutableMapOf<Long, ItemCategoryTransferSelectableBinding>()
    private var permiteTraspaso = false
    private val seleccionadas = mutableSetOf<Long>()

    private val viewModel: CloseMonthViewModel by lazy {
        val repo = (application as App).repository
        ViewModelProvider(this, FabricaViewModel { CloseMonthViewModel(repo, mesId) })[CloseMonthViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mesId = intent.getLongExtra(EXTRA_MES_ID, -1)

        binding = ActivityCloseMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }
        binding.bannerNotaTraspasos.setMensaje(getString(R.string.cerrar_mes_nota_traspasos))
        binding.btnConfirmarCierre.setOnClickListener { confirmarConDialogo() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.estado.collect { estado ->
                    if (estado != null) pintarEstado(estado)
                }
            }
        }
    }

    private fun pintarEstado(estado: CloseMonthViewModel.EstadoCierre) {
        val mes = estado.mes
        binding.tvSobrante.text = getString(R.string.cerrar_mes_sobrante_formato, Formato.moneda(mes.totalRestante))
        permiteTraspaso = estado.permiteTraspaso

        val categoriasConSobrante = mes.categorias.filter {
            it.tipo != TipoCategoria.AHORRO && it.restante > 0.0
        }

        if (!permiteTraspaso || categoriasConSobrante.isEmpty()) {
            binding.tvExplicacionReparto.visibility = android.view.View.GONE
            binding.contenedorCategorias.visibility = android.view.View.GONE
            return
        }

        val indiceSiguiente = mes.mes + 1
        val anioSiguiente = if (indiceSiguiente > 12) mes.anio + 1 else mes.anio
        val mesSiguiente = if (indiceSiguiente > 12) 1 else indiceSiguiente
        val nombreMesSiguiente = Formato.nombreMes(mesSiguiente, anioSiguiente)

        binding.tvExplicacionReparto.text = getString(R.string.cerrar_mes_explicacion_reparto, nombreMesSiguiente)
        binding.tvExplicacionReparto.visibility = android.view.View.VISIBLE
        binding.contenedorCategorias.visibility = android.view.View.VISIBLE

        if (binding.contenedorCategorias.childCount == 0) {
            categoriasConSobrante.forEach { categoria ->
                crearFilaCategoria(categoria, nombreMesSiguiente)
                seleccionadas.add(categoria.id) // por defecto todas traspasan
            }
        }
    }

    private fun crearFilaCategoria(categoria: Categoria, nombreMesSiguiente: String) {
        val filaBinding = ItemCategoryTransferSelectableBinding.inflate(
            layoutInflater, binding.contenedorCategorias, false
        )
        filasPorCategoria[categoria.id] = filaBinding

        filaBinding.tvBadgeCategoria.text = categoria.tipo.etiqueta
        filaBinding.tvBadgeCategoria.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, categoria.tipo.colorResId))
        filaBinding.tvImporteSobrante.text = Formato.moneda(categoria.restante)
        filaBinding.checkTraspasar.isChecked = true
        actualizarDestino(filaBinding, seleccionado = true, nombreMesSiguiente)

        filaBinding.checkTraspasar.setOnCheckedChangeListener { _, marcado ->
            if (marcado) seleccionadas.add(categoria.id) else seleccionadas.remove(categoria.id)
            actualizarDestino(filaBinding, marcado, nombreMesSiguiente)
        }

        binding.contenedorCategorias.addView(filaBinding.root)
    }

    private fun actualizarDestino(fila: ItemCategoryTransferSelectableBinding, seleccionado: Boolean, nombreMesSiguiente: String) {
        fila.tvDestino.text = if (seleccionado) {
            getString(R.string.cerrar_mes_destino_siguiente_formato, nombreMesSiguiente)
        } else {
            getString(R.string.cerrar_mes_destino_ahorro)
        }
        fila.tvDestino.setTextColor(
            ContextCompat.getColor(this, if (seleccionado) R.color.verde_principal else R.color.verde_ahorro)
        )
    }

    private fun confirmarConDialogo() {
        AlertDialog.Builder(this, R.style.ThemeOverlay_WarthogCash_Dialog)
            .setTitle(R.string.cerrar_mes_titulo)
            .setMessage(R.string.cerrar_mes_resumen)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.cerrar_mes_boton_confirmar) { _, _ ->
                binding.btnConfirmarCierre.isEnabled = false
                val categoriasATraspasar = if (permiteTraspaso) seleccionadas.toSet() else emptySet()
                lifecycleScope.launch {
                    viewModel.confirmarCierre(categoriasATraspasar)
                    finish()
                }
            }
            .show()
    }
}
