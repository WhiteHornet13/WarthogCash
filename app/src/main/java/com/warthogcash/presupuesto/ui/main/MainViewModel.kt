package com.warthogcash.presupuesto.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(repository: PresupuestoRepository) : ViewModel() {

    val mesActual: StateFlow<Presupuesto?> = repository.observarMesActual()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
