package com.warthogcash.presupuesto.ui.fixedexpenses

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.warthogcash.presupuesto.databinding.ItemFixedExpenseCardBinding
import com.warthogcash.presupuesto.domain.model.GastoFijo
import com.warthogcash.presupuesto.util.Formato

class FixedExpenseAdapter(
    private val alPulsarEditar: (GastoFijo) -> Unit,
    private val alPulsarEliminar: (GastoFijo) -> Unit
) : RecyclerView.Adapter<FixedExpenseAdapter.GastoFijoViewHolder>() {

    private var gastosFijos: List<GastoFijo> = emptyList()

    fun actualizar(nuevos: List<GastoFijo>) {
        gastosFijos = nuevos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoFijoViewHolder {
        val binding = ItemFixedExpenseCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GastoFijoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GastoFijoViewHolder, position: Int) {
        holder.bind(gastosFijos[position])
    }

    override fun getItemCount(): Int = gastosFijos.size

    inner class GastoFijoViewHolder(
        private val binding: ItemFixedExpenseCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gastoFijo: GastoFijo) {
            val contexto = binding.root.context

            binding.tvComentarioGastoFijo.text = gastoFijo.comentario
                ?.takeIf { it.isNotBlank() } ?: gastoFijo.tipo.etiqueta
            binding.tvCosteGastoFijo.text = Formato.moneda(gastoFijo.coste)

            binding.tvBadgeCategoria.text = gastoFijo.tipo.etiqueta
            binding.tvBadgeCategoria.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(contexto, gastoFijo.tipo.colorResId))

            binding.btnEditar.setOnClickListener { alPulsarEditar(gastoFijo) }
            binding.btnEliminar.setOnClickListener { alPulsarEliminar(gastoFijo) }
        }
    }
}