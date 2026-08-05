package com.warthogcash.presupuesto.ui.fixedexpenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.GastoFijo
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FixedExpensesViewModel(private val repository: PresupuestoRepository) : ViewModel() {

    val gastosFijos: StateFlow<List<GastoFijo>> = repository.observarGastosFijos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun eliminar(id: Long) {
        viewModelScope.launch { repository.eliminarGastoFijo(id) }
    }
}