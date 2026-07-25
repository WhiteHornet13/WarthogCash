package com.warthogcash.presupuesto.ui.main;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0019\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/warthogcash/presupuesto/ui/main/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "(Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;)V", "mesActual", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "getMesActual", "()Lkotlinx/coroutines/flow/StateFlow;", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> mesActual = null;
    
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> getMesActual() {
        return null;
    }
}