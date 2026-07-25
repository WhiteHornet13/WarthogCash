package com.warthogcash.presupuesto.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.GastoDetallado
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Especificación de pantalla "Historial de gastos": pantalla genérica con
 * dos modos, sin filtro o filtrada por categoría, según [tipoFiltro]
 * (sección 1/4.1/4.2). Ordenados por fecha, más reciente primero.
 */
class ExpenseHistoryViewModel(
    private val repository: PresupuestoRepository,
    private val mesId: Long,
    private val tipoFiltro: TipoCategoria?
) : ViewModel() {

    private val _mes = MutableStateFlow<Presupuesto?>(null)
    val mes: StateFlow<Presupuesto?> = _mes.asStateFlow()

    private val _gastos = MutableStateFlow<List<GastoDetallado>>(emptyList())
    val gastos: StateFlow<List<GastoDetallado>> = _gastos.asStateFlow()

    init {
        viewModelScope.launch {
            _mes.value = repository.obtenerMesPorId(mesId)
            val resultado = if (tipoFiltro != null) {
                repository.obtenerGastosDeMesFiltrados(mesId, tipoFiltro)
            } else {
                repository.obtenerGastosDeMes(mesId)
            }
            // 4.1: ordenados por fecha, más reciente primero.
            _gastos.value = resultado.sortedByDescending { it.gasto.fecha }
        }
    }
}
