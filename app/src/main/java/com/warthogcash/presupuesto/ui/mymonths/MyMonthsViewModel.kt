package com.warthogcash.presupuesto.ui.mymonths

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warthogcash.presupuesto.domain.model.Presupuesto
import com.warthogcash.presupuesto.domain.repository.PresupuestoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Especificación de pantalla "Mis meses", sección 4.2: carga incremental
 * (paginación) disparada por la proximidad del usuario al final del scroll.
 *
 * El tamaño de página "ideal" se define en la spec como dinámico (el
 * máximo de meses que quepan en la pantalla visible del dispositivo).
 * Calcular ese número exacto depende de medidas de layout en tiempo de
 * render y queda fuera del alcance actual; se usa aquí un tamaño de
 * página fijo razonable como aproximación, documentado como asunción.
 */
class MyMonthsViewModel(private val repository: PresupuestoRepository) : ViewModel() {

    companion object {
        private const val TAMANO_PAGINA = 8
    }

    private val _meses = MutableStateFlow<List<Presupuesto>>(emptyList())
    val meses: StateFlow<List<Presupuesto>> = _meses.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private var offset = 0
    private var hayMasPaginas = true



    fun cargarSiguientePagina() {
        if (_cargando.value || !hayMasPaginas) return
        viewModelScope.launch {
            _cargando.value = true
            val pagina = repository.obtenerPaginaMeses(TAMANO_PAGINA, offset)
            offset += pagina.size
            if (pagina.size < TAMANO_PAGINA) hayMasPaginas = false
            _meses.value = _meses.value + pagina
            _cargando.value = false
        }
    }

    fun recargar() {
        val cantidadActual = _meses.value.size.coerceAtLeast(TAMANO_PAGINA)
        offset = 0
        hayMasPaginas = true
        viewModelScope.launch {
            _cargando.value = true
            val pagina = repository.obtenerPaginaMeses(cantidadActual, 0)
            offset = pagina.size
            if (pagina.size < cantidadActual) hayMasPaginas = false
            _meses.value = pagina
            _cargando.value = false
        }
    }
}
