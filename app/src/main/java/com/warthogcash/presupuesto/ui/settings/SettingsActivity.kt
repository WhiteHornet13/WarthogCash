package com.warthogcash.presupuesto.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivitySettingsBinding
import com.warthogcash.presupuesto.databinding.ItemCategoryPercentInputBinding
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.util.PorcentajesPredefinidos
import android.content.Intent
import com.warthogcash.presupuesto.util.UmbralesColores


/**
 * Pantalla de opciones de la app. De momento solo contiene los
 * porcentajes predefinidos por categoría, usados como valor de partida
 * en "Crear mes nuevo"; está pensada para acoger más ajustes en el futuro.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var filas: Map<TipoCategoria, ItemCategoryPercentInputBinding>
    private lateinit var predefinidos: PorcentajesPredefinidos

    private lateinit var umbrales: UmbralesColores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        predefinidos = PorcentajesPredefinidos(this)

        umbrales = UmbralesColores(this)

        binding.btnVolver.setOnClickListener { finish() }

        binding.filaGastosFijos.setOnClickListener {
            startActivity(Intent(this, com.warthogcash.presupuesto.ui.fixedexpenses.FixedExpensesActivity::class.java))
        }

        binding.filaGastosFijos.setOnClickListener {
            startActivity(Intent(this, com.warthogcash.presupuesto.ui.fixedexpenses.FixedExpensesActivity::class.java))
        }

        binding.filaPorcentajesPredefinidos.setOnClickListener {
            val mostrar = binding.contenedorPorcentajes.visibility != android.view.View.VISIBLE
            binding.contenedorPorcentajes.visibility = if (mostrar) android.view.View.VISIBLE else android.view.View.GONE
            binding.ivChevronPorcentajes.text = if (mostrar) "⌄" else "›"
        }

        binding.filaUmbralesColores.setOnClickListener {
            val mostrar = binding.contenedorUmbrales.visibility != android.view.View.VISIBLE
            binding.contenedorUmbrales.visibility = if (mostrar) android.view.View.VISIBLE else android.view.View.GONE
            binding.ivChevronUmbrales.text = if (mostrar) "⌄" else "›"
        }

        filas = mapOf(
            TipoCategoria.GENERAL to binding.filaGeneral,
            TipoCategoria.AHORRO to binding.filaAhorro,
            TipoCategoria.INVERSION to binding.filaInversion,
            TipoCategoria.OCIO to binding.filaOcio,
            TipoCategoria.CULTURA to binding.filaCultura
        )

        filas.forEach { (tipo, filaBinding) ->
            filaBinding.tvNombreCategoria.text = tipo.etiqueta
            val valor = predefinidos.obtener(tipo)
            if (valor > 0.0) {
                filaBinding.etPorcentaje.setText(formatearSuma(valor))
            }
        }

        binding.filaUmbralCategoriaMedio.tvNombreCategoria.text = getString(R.string.ajustes_umbral_categoria_medio)
        binding.filaUmbralCategoriaMedio.etPorcentaje.setText(formatearSuma(umbrales.umbralCategoriaMedio))

        binding.filaUmbralCategoriaAlto.tvNombreCategoria.text = getString(R.string.ajustes_umbral_categoria_alto)
        binding.filaUmbralCategoriaAlto.etPorcentaje.setText(formatearSuma(umbrales.umbralCategoriaAlto))

        binding.filaUmbralMesMedio.tvNombreCategoria.text = getString(R.string.ajustes_umbral_mes_medio)
        binding.filaUmbralMesMedio.etPorcentaje.setText(formatearSuma(umbrales.umbralMesMedio))

        binding.filaUmbralMesAlto.tvNombreCategoria.text = getString(R.string.ajustes_umbral_mes_alto)
        binding.filaUmbralMesAlto.etPorcentaje.setText(formatearSuma(umbrales.umbralMesAlto))

        binding.btnGuardarUmbrales.setOnClickListener { guardarUmbrales() }
        binding.btnRestaurarUmbrales.setOnClickListener { restaurarUmbrales() }

        binding.btnGuardarAjustes.setOnClickListener { guardarAjustes() }
    }

    private fun guardarAjustes() {
        val valores = filas.mapValues { (_, fila) ->
            fila.etPorcentaje.text.toString().toDoubleOrNull() ?: 0.0
        }
        val suma = valores.values.sum()

        // Igual que en "Crear mes nuevo": si el usuario rellena algo,
        // debe sumar 100%. Si lo deja todo a 0, se guarda vacío (sin
        // predefinidos) y no se aplica esta validación.
        if (suma > 0.0 && Math.abs(suma - 100.0) > 0.01) {
            binding.tvErrorPorcentaje.visibility = android.view.View.VISIBLE
            binding.tvErrorPorcentaje.text = getString(
                R.string.crear_mes_error_porcentaje,
                formatearSuma(suma)
            )
            return
        }
        binding.tvErrorPorcentaje.visibility = android.view.View.GONE

        predefinidos.guardar(valores)
        Toast.makeText(this, R.string.ajustes_guardado_confirmacion, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun guardarUmbrales() {
        val medio = binding.filaUmbralCategoriaMedio.etPorcentaje.text.toString().toDoubleOrNull()
        val alto = binding.filaUmbralCategoriaAlto.etPorcentaje.text.toString().toDoubleOrNull()
        val mesMedio = binding.filaUmbralMesMedio.etPorcentaje.text.toString().toDoubleOrNull()
        val mesAlto = binding.filaUmbralMesAlto.etPorcentaje.text.toString().toDoubleOrNull()

        if (medio == null || alto == null || mesMedio == null || mesAlto == null) {
            Toast.makeText(this, R.string.ajustes_umbrales_error, Toast.LENGTH_SHORT).show()
            return
        }
        if (medio >= alto || mesMedio >= mesAlto) {
            Toast.makeText(this, R.string.ajustes_umbrales_error_orden, Toast.LENGTH_SHORT).show()
            return
        }

        umbrales.umbralCategoriaMedio = medio
        umbrales.umbralCategoriaAlto = alto
        umbrales.umbralMesMedio = mesMedio
        umbrales.umbralMesAlto = mesAlto
        Toast.makeText(this, R.string.ajustes_guardado_confirmacion, Toast.LENGTH_SHORT).show()
    }

    private fun restaurarUmbrales() {
        umbrales.restaurarPorDefecto()
        binding.filaUmbralCategoriaMedio.etPorcentaje.setText(formatearSuma(umbrales.umbralCategoriaMedio))
        binding.filaUmbralCategoriaAlto.etPorcentaje.setText(formatearSuma(umbrales.umbralCategoriaAlto))
        binding.filaUmbralMesMedio.etPorcentaje.setText(formatearSuma(umbrales.umbralMesMedio))
        binding.filaUmbralMesAlto.etPorcentaje.setText(formatearSuma(umbrales.umbralMesAlto))
        Toast.makeText(this, R.string.ajustes_guardado_confirmacion, Toast.LENGTH_SHORT).show()
    }

    private fun formatearSuma(valor: Double): String =
        if (valor == valor.toLong().toDouble()) valor.toLong().toString() else valor.toString()
}