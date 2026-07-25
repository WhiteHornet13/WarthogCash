package com.warthogcash.presupuesto.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory genérica muy simple para no depender de ningún framework de DI
 * (ver nota en [com.warthogcash.presupuesto.App]). Recibe un lambda que
 * construye el ViewModel concreto usando las dependencias ya resueltas.
 */
class FabricaViewModel<T : ViewModel>(private val crear: () -> T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM = crear() as VM
}
