package com.warthogcash.presupuesto.ui.closemonth

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityCloseMonthBinding
import com.warthogcash.presupuesto.util.FabricaViewModel
import com.warthogcash.presupuesto.util.Formato
import kotlinx.coroutines.launch

/**
 * Ver nota de alcance en [CloseMonthViewModel]: esta pantalla implementa
 * una versión mínima de "Cerrar mes · paso de traspasos" al no existir
 * especificación detallada de esa pantalla en la documentación disponible.
 */
class CloseMonthActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MES_ID = "extra_mes_id"
    }

    private lateinit var binding: ActivityCloseMonthBinding

    private val viewModel: CloseMonthViewModel by lazy {
        val repo = (application as App).repository
        val mesId = intent.getLongExtra(EXTRA_MES_ID, -1)
        ViewModelProvider(this, FabricaViewModel { CloseMonthViewModel(repo, mesId) })[CloseMonthViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCloseMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }
        binding.bannerNotaTraspasos.setMensaje(getString(R.string.cerrar_mes_nota_traspasos))

        binding.btnConfirmarCierre.setOnClickListener { confirmarConDialogo() }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mes.collect { mes ->
                    if (mes == null) return@collect
                    binding.tvSobrante.text = getString(
                        R.string.cerrar_mes_sobrante_formato,
                        Formato.moneda(mes.totalRestante)
                    )
                }
            }
        }
    }

    private fun confirmarConDialogo() {
        AlertDialog.Builder(this)
            .setTitle(R.string.cerrar_mes_titulo)
            .setMessage(R.string.cerrar_mes_resumen)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.cerrar_mes_boton_confirmar) { _, _ ->
                binding.btnConfirmarCierre.isEnabled = false
                lifecycleScope.launch {
                    viewModel.confirmarCierre()
                    finish()
                }
            }
            .show()
    }
}
