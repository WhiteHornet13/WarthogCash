package com.warthogcash.presupuesto.ui.addexpense;

/**
 * Especificación de pantalla "Añadir gasto". El gasto se asocia siempre
 * al mes de origen de la navegación (sección 4.5); esta pantalla nunca
 * presenta selector de mes, hereda el contexto vía [mesId].
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J(\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/warthogcash/presupuesto/ui/addexpense/AddExpenseViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "mesId", "", "(Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;J)V", "_mes", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "mes", "Lkotlinx/coroutines/flow/StateFlow;", "getMes", "()Lkotlinx/coroutines/flow/StateFlow;", "guardarGasto", "categoriaId", "importe", "", "descripcion", "", "(JDLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class AddExpenseViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository = null;
    private final long mesId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> _mes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> mes = null;
    
    public AddExpenseViewModel(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository, long mesId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> getMes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object guardarGasto(long categoriaId, double importe, @org.jetbrains.annotations.Nullable()
    java.lang.String descripcion, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
}