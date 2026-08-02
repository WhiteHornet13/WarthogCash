package com.warthogcash.presupuesto.ui.createmonth;

/**
 * Especificación de pantalla "Crear mes nuevo". Sección 4.4: si el mes
 * anterior más reciente (el actual, hasta que se cree este nuevo) sigue
 * abierto, se muestra un aviso; esta pantalla nunca ofrece traspasar
 * sobrante (eso pertenece al flujo de cierre de mes).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J:\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00120\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0016R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/warthogcash/presupuesto/ui/createmonth/CreateMonthViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "(Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;)V", "_mesAnteriorAbierto", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "mesAnteriorAbierto", "Lkotlinx/coroutines/flow/StateFlow;", "getMesAnteriorAbierto", "()Lkotlinx/coroutines/flow/StateFlow;", "crearMes", "", "mes", "", "anio", "dineroDisponible", "", "porcentajes", "", "Lcom/warthogcash/presupuesto/domain/model/TipoCategoria;", "(IIDLjava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CreateMonthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> _mesAnteriorAbierto = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> mesAnteriorAbierto = null;
    
    public CreateMonthViewModel(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.warthogcash.presupuesto.domain.model.Presupuesto> getMesAnteriorAbierto() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object crearMes(int mes, int anio, double dineroDisponible, @org.jetbrains.annotations.NotNull()
    java.util.Map<com.warthogcash.presupuesto.domain.model.TipoCategoria, java.lang.Double> porcentajes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
}