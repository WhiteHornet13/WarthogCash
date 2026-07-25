package com.warthogcash.presupuesto.ui.monthdetail;

/**
 * Sirve tanto a "Detalle de mes anterior (abierto)" como a "Detalle de mes
 * cerrado": ambas comparten estructura visual y difieren solo en qué
 * bloques se muestran, según el estado del [Presupuesto] cargado. Ver
 * especificación técnica, sección 7 (reutilización de bloques de interfaz).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0012\u001a\u00020\u0013R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/warthogcash/presupuesto/ui/monthdetail/MonthDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "mesId", "", "(Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;J)V", "_mes", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "_mostrarBotonCerrar", "", "mes", "Lkotlinx/coroutines/flow/StateFlow;", "getMes", "()Lkotlinx/coroutines/flow/StateFlow;", "mostrarBotonCerrar", "getMostrarBotonCerrar", "recargar", "", "app_debug"})
public final class MonthDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository = null;
    private final long mesId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> _mes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> mes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _mostrarBotonCerrar = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> mostrarBotonCerrar = null;
    
    public MonthDetailViewModel(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository, long mesId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> getMes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getMostrarBotonCerrar() {
        return null;
    }
    
    public final void recargar() {
    }
}