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
 * Pantalla "Cerrar mes · paso de traspasos", referenciada desde "Detalle
 * de mes anterior (abierto)" (sección 4.3) pero SIN especificación propia
 * en la documentación de pantallas disponible: no se detalla ahí ninguna
 * lógica de traspaso de sobrante entre meses.
 *
 * Implementación mínima y honesta mientras no exista esa especificación:
 * se muestra el sobrante total del mes y se permite cerrarlo (pasa a
 * estado CERRADO), sin mover fondos a ningún otro mes. Revisar y ampliar
 * en cuanto se disponga de la especificación de esta pantalla.
 */
class CloseMonthViewModel(
    private val repository: PresupuestoRepository,
    private val mesId: Long
) : ViewModel() {

    private val _mes = MutableStateFlow<Presupuesto?>(null)
    val mes: StateFlow<Presupuesto?> = _mes.asStateFlow()

    init {
        viewModelScope.launch { _mes.value = repository.obtenerMesPorId(mesId) }
    }

    suspend fun confirmarCierre() {
        repository.cerrarMes(mesId)
    }
}
