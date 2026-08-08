package com.warthogcash.presupuesto.ui.export

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.warthogcash.presupuesto.App
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ActivityExportBinding
import com.warthogcash.presupuesto.util.BackupJson
import com.warthogcash.presupuesto.util.CsvExporter
import kotlinx.coroutines.launch
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.appcompat.app.AlertDialog
import com.warthogcash.presupuesto.util.BackupJson.BackupInvalidoException
import java.io.InputStreamReader
import com.warthogcash.presupuesto.util.PorcentajesPredefinidos
import com.warthogcash.presupuesto.domain.model.TipoCategoria


/**
 * Pantalla "Exportar datos", accesible desde la hoja "Funciones" de
 * "Mis meses". Dos exportaciones independientes:
 * - Copia de seguridad completa en JSON (restaurable en esta app).
 * - CSV del historial de gastos, para Excel/OpenOffice (no restaurable).
 */
class ExportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExportBinding
    private val repo by lazy { (application as App).repository }

    private val selectorArchivoJson = registerForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri -> if (uri != null) exportarBackup(uri) }

    private val selectorArchivoCsv = registerForActivityResult(
        ActivityResultContracts.CreateDocument("text/csv")
    ) { uri -> if (uri != null) exportarCsv(uri) }

    private val selectorArchivoRestaurar = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri -> if (uri != null) leerYValidarBackup(uri) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener { finish() }

        val marcaTiempo = SimpleDateFormat("yyyyMMdd_HHmm", Locale("es", "ES")).format(Date())

        binding.btnExportarBackup.setOnClickListener {
            selectorArchivoJson.launch("warthogcash_backup_$marcaTiempo.json")
        }
        binding.btnExportarCsv.setOnClickListener {
            selectorArchivoCsv.launch("warthogcash_gastos_$marcaTiempo.csv")
        }
        binding.btnRestaurar.setOnClickListener {
            selectorArchivoRestaurar.launch(arrayOf("application/json"))
        }

        lifecycleScope.launch { actualizarEstadoRestaurar() }
    }

    private fun exportarBackup(uri: Uri) {
        lifecycleScope.launch {
            try {
                val meses = repo.obtenerTodoParaBackup()
                val gastosFijos = repo.obtenerGastosFijos()
                val predefinidos = PorcentajesPredefinidos(this@ExportActivity)
                val mapaPredefinidos = TipoCategoria.entries.associateWith { predefinidos.obtener(it) }
                escribirEnUri(uri, BackupJson.generar(meses, gastosFijos, mapaPredefinidos))
                Toast.makeText(this@ExportActivity, R.string.exportar_confirmacion_formato, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@ExportActivity, R.string.exportar_error_formato, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun exportarCsv(uri: Uri) {
        lifecycleScope.launch {
            try {
                val meses = repo.obtenerTodoParaBackup()
                escribirEnUri(uri, CsvExporter.generar(meses))
                Toast.makeText(this@ExportActivity, R.string.exportar_confirmacion_formato, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@ExportActivity, R.string.exportar_error_formato, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun escribirEnUri(uri: Uri, contenido: String) {
        contentResolver.openOutputStream(uri)?.use { salida ->
            OutputStreamWriter(salida, Charsets.UTF_8).use { it.write(contenido) }
        }
    }

    private suspend fun actualizarEstadoRestaurar() {
        val cantidad = repo.contarMeses()
        val bloqueado = cantidad >= 2
        binding.bannerRestaurarBloqueado.visibility = if (bloqueado) android.view.View.VISIBLE else android.view.View.GONE
        if (bloqueado) binding.bannerRestaurarBloqueado.setMensaje(R.string.restaurar_bloqueado)
        binding.btnRestaurar.isEnabled = !bloqueado
    }

    private fun leerYValidarBackup(uri: Uri) {
        lifecycleScope.launch {
            try {
                val contenido = contentResolver.openInputStream(uri)?.use { entrada ->
                    InputStreamReader(entrada, Charsets.UTF_8).readText()
                } ?: throw BackupInvalidoException("No se pudo leer el archivo")

                val backup = BackupJson.parsear(contenido)
                decidirEstrategiaYRestaurar(backup)
            } catch (e: BackupInvalidoException) {
                Toast.makeText(this@ExportActivity, R.string.restaurar_error_archivo, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this@ExportActivity, R.string.restaurar_error_generico, Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun decidirEstrategiaYRestaurar(backup: com.warthogcash.presupuesto.util.BackupJson.BackupParseado) {
        when (repo.contarMeses()) {
            0 -> ejecutarRestauracion(backup, mesIdAConservar = null)
            1 -> {
                val mesActual = repo.obtenerMesActual() // único mes existente en este caso
                if (mesActual == null) {
                    ejecutarRestauracion(backup, mesIdAConservar = null)
                    return
                }
                AlertDialog.Builder(this, R.style.ThemeOverlay_WarthogCash_Dialog)
                    .setTitle(R.string.restaurar_confirmar_titulo)
                    .setMessage(getString(R.string.restaurar_confirmar_mensaje_formato, mesActual.nombreMesAnio))
                    .setNegativeButton(getString(R.string.restaurar_boton_conservar, mesActual.nombreMesAnio)) { _, _ ->
                        lifecycleScope.launch { ejecutarRestauracion(backup, mesIdAConservar = mesActual.id) }
                    }
                    .setPositiveButton(R.string.restaurar_boton_pisar) { _, _ ->
                        lifecycleScope.launch { ejecutarRestauracion(backup, mesIdAConservar = null) }
                    }
                    .show()
            }
            else -> Toast.makeText(this, R.string.restaurar_bloqueado, Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun ejecutarRestauracion(backup: com.warthogcash.presupuesto.util.BackupJson.BackupParseado, mesIdAConservar: Long?) {
        try {
            repo.restaurarBackup(backup.meses, backup.gastosFijos, mesIdAConservar)
            if (backup.porcentajesPredefinidos.isNotEmpty()) {
                PorcentajesPredefinidos(this).guardar(backup.porcentajesPredefinidos)
            }
            Toast.makeText(this, R.string.restaurar_exito, Toast.LENGTH_SHORT).show()
            actualizarEstadoRestaurar()
        } catch (e: Exception) {
            Toast.makeText(this, R.string.restaurar_error_generico, Toast.LENGTH_LONG).show()
        }
    }
}