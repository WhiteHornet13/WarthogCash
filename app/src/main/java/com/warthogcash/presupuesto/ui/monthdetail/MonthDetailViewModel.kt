package com.warthogcash.presupuesto.ui.monthdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Sirve tanto a "Detalle de mes anterior (abierto)" como a "Detalle de mes
 * cerrado": ambas comparten estructura visual y difieren solo en qué
 * bloques se muestran, según el estado del [Presupuesto] cargado. Ver
 * especificación técnica, sección 7 (reutilización de bloques de interfaz).
 */
class MonthDetailViewModel(
    private val repository: PresupuestoRepository,
    private val mesId: Long
) : ViewModel() {

    private val _mes = MutableStateFlow<Presupuesto?>(null)
    val mes: StateFlow<Presupuesto?> = _mes.asStateFlow()

    private val _mostrarBotonCerrar = MutableStateFlow(false)
    val mostrarBotonCerrar: StateFlow<Boolean> = _mostrarBotonCerrar.asStateFlow()

    init {
        recargar()
    }

    fun recargar() {
        viewModelScope.launch {
            val presupuesto = repository.obtenerMesPorId(mesId)
            _mes.value = presupuesto
            // Spec "Detalle de mes anterior (abierto)", 4.3: el botón
            // "Cerrar mes" solo se muestra si existe un mes actual distinto.
            _mostrarBotonCerrar.value = repository.existeMesActualDistintoDe(mesId)
        }
    }
}
