package com.warthogcash.presupuesto.ui.mymonths;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0014\u001a\u00020\u0015R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/warthogcash/presupuesto/ui/mymonths/MyMonthsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "(Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;)V", "_cargando", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_meses", "", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "cargando", "Lkotlinx/coroutines/flow/StateFlow;", "getCargando", "()Lkotlinx/coroutines/flow/StateFlow;", "hayMasPaginas", "meses", "getMeses", "offset", "", "cargarSiguientePagina", "", "Companion", "app_debug"})
public final class MyMonthsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository = null;
    private static final int TAMANO_PAGINA = 8;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.warthogcash.presupuesto.domain.model.Presupuesto>> _meses = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.warthogcash.presupuesto.domain.model.Presupuesto>> meses = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _cargando = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> cargando = null;
    private int offset = 0;
    private boolean hayMasPaginas = true;
    @org.jetbrains.annotations.NotNull()
    public static final com.warthogcash.presupuesto.ui.mymonths.MyMonthsViewModel.Companion Companion = null;
    
    public MyMonthsViewModel(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.repository.PresupuestoRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.warthogcash.presupuesto.domain.model.Presupuesto>> getMeses() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getCargando() {
        return null;
    }
    
    public final void cargarSiguientePagina() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/warthogcash/presupuesto/ui/mymonths/MyMonthsViewModel$Companion;", "", "()V", "TAMANO_PAGINA", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}