package com.warthogcash.presupuesto.ui.closemonth;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/warthogcash/presupuesto/ui/closemonth/CloseMonthViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "mesId", "", "(Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;J)V", "_mes", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "mes", "Lkotlinx/coroutines/flow/StateFlow;", "getMes", "()Lkotlinx/coroutines/flow/StateFlow;", "confirmarCierre", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CloseMonthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository = null;
    private final long mesId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> _mes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> mes = null;
    
    public CloseMonthViewModel(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository, long mesId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> getMes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object confirmarCierre(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}