package com.warthogcash.presupuesto.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ViewMonthSummaryHeaderBinding
import com.warthogcash.presupuesto.domain.model.EstadoPresupuesto
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.util.Formato

/**
 * Bloque reutilizable "Header / Resumen de mes", usado en Pantalla
 * principal, Detalle de mes anterior (abierto) y Detalle de mes cerrado
 * (especificación técnica, sección 7: componentes reutilizables entre
 * pantallas, no duplicados por pantalla).
 */
class MonthSummaryHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewMonthSummaryHeaderBinding.inflate(
        android.view.LayoutInflater.from(context), this, true
    )

    /** Variante para la Pantalla principal: sin flecha, con icono calendario, sin badge. */
    fun mostrarComoPantallaPrincipal(presupuesto: Presupuesto, alPulsarCalendario: () -> Unit) {
        binding.btnVolver.visibility = android.view.View.GONE
        binding.tvTituloMes.visibility = android.view.View.GONE
        binding.tvEtiquetaDisponible.visibility = android.view.View.VISIBLE
        binding.ivCalendario.visibility = android.view.View.VISIBLE
        binding.tvEstadoBadge.visibility = android.view.View.GONE

        binding.contenedorHeader.setBackgroundResource(R.drawable.bg_header_rounded)
        binding.ivCalendario.setOnClickListener { alPulsarCalendario() }

        rellenarImportes(presupuesto)
    }

    /** Variante para pantallas de detalle: con flecha + nombre de mes, badge de estado. */
    fun mostrarComoDetalle(presupuesto: Presupuesto, alPulsarVolver: () -> Unit) {
        binding.btnVolver.visibility = android.view.View.VISIBLE
        binding.tvTituloMes.visibility = android.view.View.VISIBLE
        binding.tvEtiquetaDisponible.visibility = android.view.View.GONE
        binding.ivCalendario.visibility = android.view.View.GONE
        binding.tvEstadoBadge.visibility = android.view.View.VISIBLE

        binding.tvTituloMes.text = Formato.nombreMes(presupuesto.mes, presupuesto.anio)
        binding.btnVolver.setOnClickListener { alPulsarVolver() }

        val esCerrado = presupuesto.estado == EstadoPresupuesto.CERRADO
        binding.contenedorHeader.setBackgroundResource(
            if (esCerrado) R.drawable.bg_header_rounded_cerrado else R.drawable.bg_header_rounded
        )
        binding.tvEstadoBadge.text = context.getString(
            if (esCerrado) R.string.detalle_estado_cerrado else R.string.detalle_estado_abierto
        )
        binding.tvEstadoBadge.background = ContextCompat.getDrawable(
            context,
            if (esCerrado) R.drawable.bg_boton_outline_rojo else R.drawable.bg_badge_actual
        )
        binding.tvEstadoBadge.setTextColor(ContextCompat.getColor(context, R.color.blanco))

        rellenarImportes(presupuesto)
    }

    private fun rellenarImportes(presupuesto: Presupuesto) {
        binding.tvDisponible.text = Formato.moneda(presupuesto.totalRestante)
        binding.tvIngreso.text = Formato.moneda(presupuesto.dineroDisponible)
        binding.tvGastado.text = Formato.moneda(presupuesto.totalGastado)
    }
}
