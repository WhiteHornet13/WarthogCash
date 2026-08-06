package com.warthogcash.presupuesto.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Antes se usaba repository.observarMesActual() (Flow reactivo de Room),
 * pero ese Flow solo se invalida cuando cambia la tabla `presupuestos`,
 * no cuando se añade/edita/elimina un Gasto (tabla `gastos`). Eso dejaba
 * la Pantalla principal con el importe "gastado" desactualizado tras
 * eliminar un gasto desde el Historial, hasta salir y volver a entrar.
 * Se sustituye por una recarga explícita (recargar()), invocada desde
 * MainActivity.onResume(), igual que ya hacen MonthDetailActivity y
 * MyMonthsActivity para este mismo tipo de problema.
 */
class MainViewModel(private val repository: PresupuestoRepository) : ViewModel() {

    private val _mesActual = MutableStateFlow<Presupuesto?>(null)
    val mesActual: StateFlow<Presupuesto?> = _mesActual.asStateFlow()

    init {
        recargar()
    }

    fun recargar() {
        viewModelScope.launch {
            _mesActual.value = repository.obtenerMesActual()
        }
    }
}