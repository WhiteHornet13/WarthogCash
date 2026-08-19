package com.warthogcash.presupuesto.ui.closemonth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * "Cerrar mes". Regla de negocio: solo se puede repartir el sobrante hacia
 * el mes siguiente si este mes es el inmediatamente anterior al actual.
 * Cualquier otro mes abierto que se cierre manda todo su sobrante a Ahorro.
 */
class CloseMonthViewModel(
    private val repository: PresupuestoRepository,
    private val mesId: Long
) : ViewModel() {

    data class EstadoCierre(val mes: Presupuesto, val permiteTraspaso: Boolean)

    private val _estado = MutableStateFlow<EstadoCierre?>(null)
    val estado: StateFlow<EstadoCierre?> = _estado.asStateFlow()

    init {
        viewModelScope.launch {
            val mes = repository.obtenerMesPorId(mesId) ?: return@launch
            val ahorroEnNegativo = (mes.categorias.firstOrNull { it.tipo == com.warthogcash.presupuesto.domain.model.TipoCategoria.AHORRO }?.restante ?: 0.0) < 0.0
            val permiteTraspaso = repository.existeMesSiguienteInmediatoAbierto(mesId) && !ahorroEnNegativo
            _estado.value = EstadoCierre(mes, permiteTraspaso)
        }
    }

    suspend fun confirmarCierre(categoriasATraspasar: Set<Long>) {
        repository.cerrarMesConReparto(mesId, categoriasATraspasar)
    }
}