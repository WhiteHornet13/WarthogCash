package com.warthogcash.presupuesto.ui.graficas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.PresupuestoConGastos
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import com.warthogcash.presupuesto.util.EstadisticasCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GraficasViewModel(private val repository: PresupuestoRepository) : ViewModel() {

    private val _meses = MutableStateFlow<List<PresupuestoConGastos>>(emptyList())
    val meses: StateFlow<List<PresupuestoConGastos>> = _meses.asStateFlow()

    private val _aniosDisponibles = MutableStateFlow<List<Int>>(emptyList())
    val aniosDisponibles: StateFlow<List<Int>> = _aniosDisponibles.asStateFlow()

    init {
        viewModelScope.launch {
            val datos = repository.obtenerTodoParaBackup()
            _meses.value = datos
            _aniosDisponibles.value = EstadisticasCalculator.aniosDisponibles(datos)
        }
    }
}