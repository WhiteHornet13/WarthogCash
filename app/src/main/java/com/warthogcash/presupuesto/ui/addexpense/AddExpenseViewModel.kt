package com.warthogcash.presupuesto.ui.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Especificación de pantalla "Añadir gasto". El gasto se asocia siempre
 * al mes de origen de la navegación (sección 4.5); esta pantalla nunca
 * presenta selector de mes, hereda el contexto vía [mesId].
 */
class AddExpenseViewModel(
    private val repository: PresupuestoRepository,
    private val mesId: Long
) : ViewModel() {

    private val _mes = MutableStateFlow<Presupuesto?>(null)
    val mes: StateFlow<Presupuesto?> = _mes.asStateFlow()

    init {
        viewModelScope.launch {
            _mes.value = repository.obtenerMesPorId(mesId)
        }
    }

    suspend fun guardarGasto(categoriaId: Long, importe: Double, descripcion: String?): Long =
        repository.agregarGasto(categoriaId, importe, descripcion)
}
