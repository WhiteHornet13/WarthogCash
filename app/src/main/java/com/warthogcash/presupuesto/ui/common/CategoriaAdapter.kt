package com.warthogcash.presupuesto.ui.common

import android.graphics.drawable.LayerDrawable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.warthogcash.presupuesto.R
import com.warthogcash.presupuesto.databinding.ItemCategoriaCardBinding
import com.warthogcash.presupuesto.domain.model.Categoria
import com.warthogcash.presupuesto.domain.model.EstadoBarraProgreso
import com.warthogcash.presupuesto.util.Formato
import com.warthogcash.presupuesto.util.UmbralesColores

/**
 * Adapter reutilizable para la "Lista de categorías" (bloque de interfaz
 * reutilizado en Pantalla principal, Detalle de mes abierto y Detalle de
 * mes cerrado). Especificación técnica, sección 7.
 */
class CategoriaAdapter(
    private val alPulsarCategoria: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    private var categorias: List<Categoria> = emptyList()

    fun actualizar(nuevas: List<Categoria>) {
        categorias = nuevas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val binding = ItemCategoriaCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(categorias[position])
    }

    override fun getItemCount(): Int = categorias.size

    inner class CategoriaViewHolder(
        private val binding: ItemCategoriaCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(categoria: Categoria) {
            val contexto = binding.root.context
            val umbrales = UmbralesColores(contexto)
            val estado = categoria.estado(umbrales.umbralCategoriaMedio, umbrales.umbralCategoriaAlto)

            val colorEstado = when (estado) {
                EstadoBarraProgreso.NORMAL -> R.color.progreso_normal
                EstadoBarraProgreso.MEDIO -> R.color.acento_ambar
                EstadoBarraProgreso.CERCA_DEL_LIMITE -> R.color.rojo_limite
                EstadoBarraProgreso.LIMITE_SUPERADO -> R.color.rojo_limite
                EstadoBarraProgreso.RESTANTE_TRASPASADO -> R.color.azul_ocio
                EstadoBarraProgreso.RESTANTE_AHORRADO -> R.color.azul_ocio
            }

            // El punto de color junto al nombre refleja el mismo estado que
            // la barra de progreso (ver mockups de "Pantalla principal").
            val textoNombre = "●  ${categoria.tipo.etiqueta}"
            binding.tvNombreCategoria.text = SpannableString(textoNombre).apply {
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(contexto, categoria.tipo.colorResId)),
                    0, 1, android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            binding.tvPorcentajeMonto.text = "${formatearPorcentaje(categoria.porcentaje)}% · ${Formato.moneda(categoria.montoAsignado)}"
            binding.tvGastado.text = "${Formato.moneda(categoria.gastado)} gastado"

            when (categoria.estado()) {
                EstadoBarraProgreso.LIMITE_SUPERADO -> {
                    binding.tvRestante.text = "límite superado: ${Formato.moneda(categoria.restante)}"
                    binding.tvRestante.setTextColor(ContextCompat.getColor(contexto, R.color.rojo_limite))
                }
                EstadoBarraProgreso.RESTANTE_TRASPASADO -> {
                    binding.tvRestante.text = "restante traspasado"
                    binding.tvRestante.setTextColor(ContextCompat.getColor(contexto, R.color.azul_ocio))
                }
                EstadoBarraProgreso.RESTANTE_AHORRADO -> {
                    binding.tvRestante.text = "restante ahorrado"
                    binding.tvRestante.setTextColor(ContextCompat.getColor(contexto, R.color.verde_ahorro))
                }
                else -> {
                    binding.tvRestante.text = "quedan ${Formato.moneda(categoria.restante)}"
                    binding.tvRestante.setTextColor(ContextCompat.getColor(contexto, R.color.texto_principal))
                }
            }

            binding.barraProgreso.progress = (categoria.progreso * 100).toInt().coerceIn(0, 100)
            val progressDrawable = binding.barraProgreso.progressDrawable
            if (progressDrawable is LayerDrawable) {
                val capaProgreso = progressDrawable.findDrawableByLayerId(android.R.id.progress)
                capaProgreso?.mutate()?.setTint(ContextCompat.getColor(contexto, colorEstado))
            }

            binding.tarjetaCategoria.setOnClickListener { alPulsarCategoria(categoria) }
        }

        private fun formatearPorcentaje(valor: Double): String =
            if (valor == valor.toLong().toDouble()) valor.toLong().toString() else valor.toString()
    }
}
