package com.warthogcash.presupuesto.ui.fixedexpenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.GastoFijo
import com.warthogcash.presupuesto.domain.model.GastoFijoAplicado
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Estado editable de un gasto fijo dentro de la pantalla de selección. */
data class GastoFijoSeleccionable(
    val gastoFijo: GastoFijo,
    val seleccionado: Boolean = true,
    val costeDeEsteMes: Double = gastoFijo.coste
)

class SelectFixedExpensesViewModel(
    private val repository: PresupuestoRepository,
    private val mesId: Long
) : ViewModel() {

    private val _items = MutableStateFlow<List<GastoFijoSeleccionable>>(emptyList())
    val items: StateFlow<List<GastoFijoSeleccionable>> = _items.asStateFlow()

    init {
        viewModelScope.launch {
            _items.value = repository.obtenerGastosFijos().map { GastoFijoSeleccionable(it) }
        }
    }

    fun alternarSeleccion(id: Long) {
        _items.value = _items.value.map {
            if (it.gastoFijo.id == id) it.copy(seleccionado = !it.seleccionado) else it
        }
    }

    fun actualizarCoste(id: Long, nuevoCoste: Double) {
        _items.value = _items.value.map {
            if (it.gastoFijo.id == id) it.copy(costeDeEsteMes = nuevoCoste) else it
        }
    }

    suspend fun confirmar() {
        val seleccionados = _items.value.filter { it.seleccionado }.map {
            GastoFijoAplicado(
                tipo = it.gastoFijo.tipo,
                coste = it.costeDeEsteMes,
                comentario = it.gastoFijo.comentario
            )
        }
        if (seleccionados.isNotEmpty()) {
            repository.aplicarGastosFijosAMes(mesId, seleccionados)
        }
    }
}