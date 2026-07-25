package com.warthogcash.presupuesto.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.warthogcash.presupuesto.databinding.ItemExpenseRowBinding
import com.warthogcash.presupuesto.domain.model.GastoDetallado
import com.warthogcash.presupuesto.util.Formato

/**
 * Adapter genérico de filas de gasto, usado tanto en modo "todos los
 * gastos del mes" como en modo filtrado por categoría (especificación de
 * pantalla "Historial de gastos", sección 4.1/4.2). Cada fila sigue
 * mostrando su etiqueta de categoría en ambos modos, por consistencia
 * visual (sección 4.2).
 */
class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.GastoViewHolder>() {

    private var gastos: List<GastoDetallado> = emptyList()

    fun actualizar(nuevos: List<GastoDetallado>) {
        gastos = nuevos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val binding = ItemExpenseRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GastoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        holder.bind(gastos[position])
    }

    override fun getItemCount(): Int = gastos.size

    class GastoViewHolder(private val binding: ItemExpenseRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GastoDetallado) {
            val descripcion = item.gasto.descripcion?.takeIf { it.isNotBlank() } ?: item.categoria.etiqueta
            binding.tvDescripcionGasto.text = descripcion
            binding.tvFechaCategoriaGasto.text = "${Formato.fechaCorta(item.gasto.fecha)} · ${item.categoria.etiqueta}"
            binding.tvImporteGasto.text = "−${Formato.moneda(item.gasto.importe)}"
        }
    }
}
