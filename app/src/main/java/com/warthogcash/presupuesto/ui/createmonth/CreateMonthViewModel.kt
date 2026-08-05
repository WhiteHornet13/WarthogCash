package com.warthogcash.presupuesto.ui.createmonth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.EstadoPresupuesto
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.model.TipoCategoria
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Especificación de pantalla "Crear mes nuevo". Sección 4.4: si el mes
 * anterior más reciente (el actual, hasta que se cree este nuevo) sigue
 * abierto, se muestra un aviso; esta pantalla nunca ofrece traspasar
 * sobrante (eso pertenece al flujo de cierre de mes).
 */
class CreateMonthViewModel(private val repository: PresupuestoRepository) : ViewModel() {

    private val _mesAnteriorAbierto = MutableStateFlow<Presupuesto?>(null)
    val mesAnteriorAbierto: StateFlow<Presupuesto?> = _mesAnteriorAbierto.asStateFlow()

    private val _mesActual = MutableStateFlow<Presupuesto?>(null)
    val mesActual: StateFlow<Presupuesto?> = _mesActual.asStateFlow()

    init {
        viewModelScope.launch {
            val actual = repository.obtenerMesActual()
            _mesActual.value = actual
            if (actual != null && actual.estado == EstadoPresupuesto.ABIERTO) {
                _mesAnteriorAbierto.value = actual
            }
        }
    }

    suspend fun crearMes(
        mes: Int,
        anio: Int,
        dineroDisponible: Double,
        porcentajes: Map<TipoCategoria, Double>
    ): Long = repository.crearMes(mes, anio, dineroDisponible, porcentajes)
}
