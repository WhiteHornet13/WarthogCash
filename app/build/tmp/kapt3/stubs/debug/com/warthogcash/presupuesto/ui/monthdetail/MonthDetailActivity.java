package com.warthogcash.presupuesto.ui.monthdetail;

/**
 * Pantalla unificada que cubre tanto "Detalle de mes anterior (abierto)"
 * como "Detalle de mes cerrado": ambas especificaciones comparten la
 * misma estructura (header + lista de categorías) y solo difieren en los
 * bloques de acción disponibles, así que se implementan como una única
 * Activity que alterna su UI según el estado del mes cargado.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0012\u0010\u0013\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0010H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lcom/warthogcash/presupuesto/ui/monthdetail/MonthDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/warthogcash/presupuesto/ui/common/CategoriaAdapter;", "binding", "Lcom/warthogcash/presupuesto/databinding/ActivityMonthDetailBinding;", "mesId", "", "viewModel", "Lcom/warthogcash/presupuesto/ui/monthdetail/MonthDetailViewModel;", "getViewModel", "()Lcom/warthogcash/presupuesto/ui/monthdetail/MonthDetailViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "abrirHistorialDeCategoria", "", "categoria", "Lcom/warthogcash/presupuesto/domain/model/Categoria;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "Companion", "app_debug"})
public final class MonthDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_MES_ID = "extra_mes_id";
    private com.warthogcash.presupuesto.databinding.ActivityMonthDetailBinding binding;
    private com.warthogcash.presupuesto.ui.common.CategoriaAdapter adapter;
    private long mesId = -1L;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.warthogcash.presupuesto.ui.monthdetail.MonthDetailActivity.Companion Companion = null;
    
    public MonthDetailActivity() {
        super();
    }
    
    private final com.warthogcash.presupuesto.ui.monthdetail.MonthDetailViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void abrirHistorialDeCategoria(com.warthogcash.presupuesto.domain.model.Categoria categoria) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/warthogcash/presupuesto/ui/monthdetail/MonthDetailActivity$Companion;", "", "()V", "EXTRA_MES_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}