package com.warthogcash.presupuesto.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.warthogcash.presupuesto.databinding.ViewWarningBannerBinding

/**
 * Bloque reutilizable "Banner de aviso" (especificación técnica, sección 7),
 * usado en "Detalle de mes cerrado" y en "Crear mes nuevo" (aviso
 * condicional cuando el mes anterior sigue abierto).
 */
class WarningBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewWarningBannerBinding.inflate(
        android.view.LayoutInflater.from(context), this, true
    )

    fun setMensaje(texto: String) {
        binding.tvMensajeBanner.text = texto
    }

    fun setMensaje(resId: Int) {
        binding.tvMensajeBanner.setText(resId)
    }
}
