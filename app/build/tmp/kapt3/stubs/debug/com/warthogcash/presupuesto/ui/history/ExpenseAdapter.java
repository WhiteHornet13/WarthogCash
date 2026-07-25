package com.warthogcash.presupuesto.ui.history;

/**
 * Adapter genérico de filas de gasto, usado tanto en modo "todos los
 * gastos del mes" como en modo filtrado por categoría (especificación de
 * pantalla "Historial de gastos", sección 4.1/4.2). Cada fila sigue
 * mostrando su etiqueta de categoría en ambos modos, por consistencia
 * visual (sección 4.2).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0014\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/warthogcash/presupuesto/ui/history/ExpenseAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/warthogcash/presupuesto/ui/history/ExpenseAdapter$GastoViewHolder;", "()V", "gastos", "", "Lcom/warthogcash/presupuesto/domain/model/GastoDetallado;", "actualizar", "", "nuevos", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "GastoViewHolder", "app_debug"})
public final class ExpenseAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.warthogcash.presupuesto.ui.history.ExpenseAdapter.GastoViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado> gastos;
    
    public ExpenseAdapter() {
        super();
    }
    
    public final void actualizar(@org.jetbrains.annotations.NotNull()
    java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado> nuevos) {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.warthogcash.presupuesto.ui.history.ExpenseAdapter.GastoViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.ui.history.ExpenseAdapter.GastoViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/warthogcash/presupuesto/ui/history/ExpenseAdapter$GastoViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/warthogcash/presupuesto/databinding/ItemExpenseRowBinding;", "(Lcom/warthogcash/presupuesto/databinding/ItemExpenseRowBinding;)V", "bind", "", "item", "Lcom/warthogcash/presupuesto/domain/model/GastoDetallado;", "app_debug"})
    public static final class GastoViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.warthogcash.presupuesto.databinding.ItemExpenseRowBinding binding = null;
        
        public GastoViewHolder(@org.jetbrains.annotations.NotNull()
        com.warthogcash.presupuesto.databinding.ItemExpenseRowBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.warthogcash.presupuesto.domain.model.GastoDetallado item) {
        }
    }
}