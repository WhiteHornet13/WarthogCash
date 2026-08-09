package com.warthogcash.presupuesto.ui.mymonths

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.warthogcash.presupuesto.databinding.ItemLoadingFooterBinding
import com.warthogcash.presupuesto.databinding.ItemMonthCardBinding
import com.warthogcash.presupuesto.databinding.ItemYearHeaderBinding
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.util.Formato
import com.warthogcash.presupuesto.domain.model.EstadoPresupuesto

private const val TIPO_CABECERA_ANIO = 0
private const val TIPO_TARJETA_MES = 1
private const val TIPO_PIE_CARGANDO = 2

private sealed class ItemLista {
    data class CabeceraAnio(val anio: Int) : ItemLista()
    data class TarjetaMes(val presupuesto: Presupuesto) : ItemLista()
    object PieCargando : ItemLista()
}

/**
 * Adapter de "Mis meses": agrupa por año (sección 4.1 de la spec) y añade
 * un indicador de carga al final mientras se resuelve la siguiente tanda
 * de la paginación incremental (sección 4.2).
 */
class MonthListAdapter(
    private val alPulsarMes: (Presupuesto) -> Unit,
    private val alMantenerPulsadoMes: (Presupuesto) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ItemLista> = emptyList()

    fun actualizar(meses: List<Presupuesto>, cargando: Boolean) {
        val nuevos = mutableListOf<ItemLista>()
        var anioAnterior: Int? = null
        for (mes in meses) {
            if (mes.anio != anioAnterior) {
                nuevos += ItemLista.CabeceraAnio(mes.anio)
                anioAnterior = mes.anio
            }
            nuevos += ItemLista.TarjetaMes(mes)
        }
        if (cargando) nuevos += ItemLista.PieCargando
        items = nuevos
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is ItemLista.CabeceraAnio -> TIPO_CABECERA_ANIO
        is ItemLista.TarjetaMes -> TIPO_TARJETA_MES
        is ItemLista.PieCargando -> TIPO_PIE_CARGANDO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TIPO_CABECERA_ANIO -> CabeceraViewHolder(ItemYearHeaderBinding.inflate(inflater, parent, false))
            TIPO_PIE_CARGANDO -> PieViewHolder(ItemLoadingFooterBinding.inflate(inflater, parent, false))
            else -> MesViewHolder(ItemMonthCardBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ItemLista.CabeceraAnio -> (holder as CabeceraViewHolder).bind(item.anio)
            is ItemLista.TarjetaMes -> (holder as MesViewHolder).bind(item.presupuesto)
            is ItemLista.PieCargando -> Unit
        }
    }

    override fun getItemCount(): Int = items.size

    private class CabeceraViewHolder(val binding: ItemYearHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anio: Int) {
            (binding.root).text = anio.toString()
        }
    }

    private class PieViewHolder(binding: ItemLoadingFooterBinding) : RecyclerView.ViewHolder(binding.root)

    private inner class MesViewHolder(val binding: ItemMonthCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mes: Presupuesto) {
            val contexto = binding.root.context

            binding.tvNombreMes.text = mes.nombreMesAnio
            binding.tvBadgeActual.visibility = if (mes.esActual) android.view.View.VISIBLE else android.view.View.GONE
            binding.tvSubtituloMes.text = contexto.getString(
                com.warthogcash.presupuesto.R.string.mis_meses_subtitulo_formato,
                Formato.moneda(mes.totalGastado),
                Formato.moneda(mes.dineroDisponible)
            )
            binding.tvRestanteMes.text = Formato.moneda(mes.totalRestante)

            val colorEstado = when {
                mes.totalGastado >= mes.dineroDisponible -> com.warthogcash.presupuesto.R.color.rojo_limite
                mes.dineroDisponible <= 0.0 -> com.warthogcash.presupuesto.R.color.progreso_normal
                (mes.totalGastado / mes.dineroDisponible) >= 0.85 -> com.warthogcash.presupuesto.R.color.acento_ambar
                else -> com.warthogcash.presupuesto.R.color.progreso_normal
            }
            binding.barraEstadoMes.progress = if (mes.dineroDisponible <= 0.0) 0
                else ((mes.totalGastado / mes.dineroDisponible) * 100).toInt().coerceIn(0, 100)
            val drawable = binding.barraEstadoMes.progressDrawable
            if (drawable is android.graphics.drawable.LayerDrawable) {
                drawable.findDrawableByLayerId(android.R.id.progress)
                    ?.mutate()?.setTint(androidx.core.content.ContextCompat.getColor(contexto, colorEstado))
            }

            val esCerrado = mes.estado == EstadoPresupuesto.CERRADO

            val (fondoRes, colorTexto, colorTextoSecundario) = when {
                mes.esActual -> Triple(
                    com.warthogcash.presupuesto.R.drawable.bg_card_mes_actual,
                    com.warthogcash.presupuesto.R.color.texto_mes_actual,
                    com.warthogcash.presupuesto.R.color.texto_mes_actual_secundario
                )
                esCerrado -> Triple(
                    com.warthogcash.presupuesto.R.drawable.bg_card_mes_cerrado,
                    com.warthogcash.presupuesto.R.color.texto_mes_cerrado,
                    com.warthogcash.presupuesto.R.color.texto_mes_cerrado_secundario
                )
                else -> Triple(
                    com.warthogcash.presupuesto.R.drawable.bg_card_mes_abierto,
                    com.warthogcash.presupuesto.R.color.texto_mes_abierto,
                    com.warthogcash.presupuesto.R.color.texto_mes_abierto_secundario
                )
            }

            binding.root.setBackgroundResource(fondoRes)
            binding.tvNombreMes.setTextColor(androidx.core.content.ContextCompat.getColor(contexto, colorTexto))
            binding.tvRestanteMes.setTextColor(androidx.core.content.ContextCompat.getColor(contexto, colorTexto))
            binding.tvSubtituloMes.setTextColor(androidx.core.content.ContextCompat.getColor(contexto, colorTextoSecundario))

            binding.root.setOnClickListener { alPulsarMes(mes) }
            binding.root.setOnLongClickListener {
                alMantenerPulsadoMes(mes)
                true
            }
        }
    }
}
