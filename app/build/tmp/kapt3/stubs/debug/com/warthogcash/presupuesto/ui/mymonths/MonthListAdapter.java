package com.warthogcash.presupuesto.ui.mymonths;

/**
 * Adapter de "Mis meses": agrupa por año (sección 4.1 de la spec) y añade
 * un indicador de carga al final mientras se resuelve la siguiente tanda
 * de la paginación incremental (sección 4.2).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\u0019\u001a\u001bB\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\u000b\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0018\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/warthogcash/presupuesto/ui/mymonths/MonthListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "alPulsarMes", "Lkotlin/Function1;", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "", "(Lkotlin/jvm/functions/Function1;)V", "items", "", "Lcom/warthogcash/presupuesto/ui/mymonths/ItemLista;", "actualizar", "meses", "cargando", "", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "CabeceraViewHolder", "MesViewHolder", "PieViewHolder", "app_debug"})
public final class MonthListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.warthogcash.presupuesto.domain.model.Presupuesto, kotlin.Unit> alPulsarMes = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<? extends com.warthogcash.presupuesto.ui.mymonths.ItemLista> items;
    
    public MonthListAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.warthogcash.presupuesto.domain.model.Presupuesto, kotlin.Unit> alPulsarMes) {
        super();
    }
    
    public final void actualizar(@org.jetbrains.annotations.NotNull()
    java.util.List<com.warthogcash.presupuesto.domain.model.Presupuesto> meses, boolean cargando) {
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2 = {"Lcom/warthogcash/presupuesto/ui/mymonths/MonthListAdapter$CabeceraViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/warthogcash/presupuesto/databinding/ItemYearHeaderBinding;", "(Lcom/warthogcash/presupuesto/databinding/ItemYearHeaderBinding;)V", "getBinding", "()Lcom/warthogcash/presupuesto/databinding/ItemYearHeaderBinding;", "bind", "", "anio", "", "app_debug"})
    static final class CabeceraViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.warthogcash.presupuesto.databinding.ItemYearHeaderBinding binding = null;
        
        public CabeceraViewHolder(@org.jetbrains.annotations.NotNull()
        com.warthogcash.presupuesto.databinding.ItemYearHeaderBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.warthogcash.presupuesto.databinding.ItemYearHeaderBinding getBinding() {
            return null;
        }
        
        public final void bind(int anio) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2 = {"Lcom/warthogcash/presupuesto/ui/mymonths/MonthListAdapter$MesViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/warthogcash/presupuesto/databinding/ItemMonthCardBinding;", "(Lcom/warthogcash/presupuesto/ui/mymonths/MonthListAdapter;Lcom/warthogcash/presupuesto/databinding/ItemMonthCardBinding;)V", "getBinding", "()Lcom/warthogcash/presupuesto/databinding/ItemMonthCardBinding;", "bind", "", "mes", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "app_debug"})
    final class MesViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.warthogcash.presupuesto.databinding.ItemMonthCardBinding binding = null;
        
        public MesViewHolder(@org.jetbrains.annotations.NotNull()
        com.warthogcash.presupuesto.databinding.ItemMonthCardBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.warthogcash.presupuesto.databinding.ItemMonthCardBinding getBinding() {
            return null;
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.warthogcash.presupuesto.domain.model.Presupuesto mes) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/warthogcash/presupuesto/ui/mymonths/MonthListAdapter$PieViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/warthogcash/presupuesto/databinding/ItemLoadingFooterBinding;", "(Lcom/warthogcash/presupuesto/databinding/ItemLoadingFooterBinding;)V", "app_debug"})
    static final class PieViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        
        public PieViewHolder(@org.jetbrains.annotations.NotNull()
        com.warthogcash.presupuesto.databinding.ItemLoadingFooterBinding binding) {
            super(null);
        }
    }
}