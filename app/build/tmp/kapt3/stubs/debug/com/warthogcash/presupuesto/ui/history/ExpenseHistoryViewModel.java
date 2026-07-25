package com.warthogcash.presupuesto.ui.history;

/**
 * Especificación de pantalla "Historial de gastos": pantalla genérica con
 * dos modos, sin filtro o filtrada por categoría, según [tipoFiltro]
 * (sección 1/4.1/4.2). Ordenados por fecha, más reciente primero.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bR\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0019\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/warthogcash/presupuesto/ui/history/ExpenseHistoryViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "mesId", "", "tipoFiltro", "Lcom/warthogcash/presupuesto/domain/model/TipoCategoria;", "(Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;JLcom/warthogcash/presupuesto/domain/model/TipoCategoria;)V", "_gastos", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/warthogcash/presupuesto/domain/model/GastoDetallado;", "_mes", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "gastos", "Lkotlinx/coroutines/flow/StateFlow;", "getGastos", "()Lkotlinx/coroutines/flow/StateFlow;", "mes", "getMes", "app_debug"})
public final class ExpenseHistoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository = null;
    private final long mesId = 0L;
    @org.jetbrains.annotations.Nullable()
    private final com.warthogcash.presupuesto.domain.model.TipoCategoria tipoFiltro = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> _mes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> mes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado>> _gastos = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado>> gastos = null;
    
    public ExpenseHistoryViewModel(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository, long mesId, @org.jetbrains.annotations.Nullable()
    com.warthogcash.presupuesto.domain.model.TipoCategoria tipoFiltro) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> getMes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado>> getGastos() {
        return null;
    }
}