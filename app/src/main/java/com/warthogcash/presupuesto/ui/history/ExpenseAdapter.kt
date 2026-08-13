package com.warthogcash.presupuesto.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.warthogcash.presupuesto.databinding.ItemExpenseRowBinding
import com.warthogcash.presupuesto.domain.model.GastoDetallado
import com.warthogcash.presupuesto.util.Formato

/**
 * Adapter genérico de filas de gasto. Desde 1.6.0 permite editar y
 * eliminar cada fila (con confirmación gestionada por la Activity),
 * salvo cuando el mes está cerrado ([editable] = false), en cuyo caso
 * los botones se ocultan.
 */
class ExpenseAdapter(
    private val alPulsarEditar: (GastoDetallado) -> Unit,
    private val alPulsarEliminar: (GastoDetallado) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.GastoViewHolder>() {

    private var gastos: List<GastoDetallado> = emptyList()
    private var editable: Boolean = true

    fun actualizar(nuevos: List<GastoDetallado>, editable: Boolean = true) {
        gastos = nuevos
        this.editable = editable
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val binding = ItemExpenseRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GastoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        holder.bind(gastos[position], editable)
    }

    override fun getItemCount(): Int = gastos.size

    inner class GastoViewHolder(private val binding: ItemExpenseRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GastoDetallado, editable: Boolean) {
            val descripcion = item.gasto.descripcion?.takeIf { it.isNotBlank() } ?: item.categoria.etiqueta
            binding.tvDescripcionGasto.text = descripcion
            binding.tvFechaCategoriaGasto.text = "${Formato.fechaCorta(item.gasto.fecha)} · ${item.categoria.etiqueta}"
            val contexto = binding.root.context
            if (item.gasto.esIngreso) {
                binding.tvImporteGasto.text = "+${Formato.moneda(item.gasto.importe)}"
                binding.tvImporteGasto.setTextColor(androidx.core.content.ContextCompat.getColor(contexto, com.warthogcash.presupuesto.R.color.verde_ahorro))
            } else {
                binding.tvImporteGasto.text = "−${Formato.moneda(item.gasto.importe)}"
                binding.tvImporteGasto.setTextColor(androidx.core.content.ContextCompat.getColor(contexto, com.warthogcash.presupuesto.R.color.texto_principal))
            }

            val puedeEditarEsteGasto = editable && !item.gasto.esIngreso && item.gasto.gastoCoberturaOrigenId == null
            binding.btnEditarGasto.visibility = if (puedeEditarEsteGasto) View.VISIBLE else View.GONE
            binding.btnEliminarGasto.visibility = if (puedeEditarEsteGasto) View.VISIBLE else View.GONE
            binding.btnEditarGasto.setOnClickListener { alPulsarEditar(item) }
            binding.btnEliminarGasto.setOnClickListener { alPulsarEliminar(item) }
        }
    }
}