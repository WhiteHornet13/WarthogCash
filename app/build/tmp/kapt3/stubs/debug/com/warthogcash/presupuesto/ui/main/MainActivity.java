package com.warthogcash.presupuesto.ui.main;

/**
 * Pantalla principal: muestra el estado del mes actual (especificación
 * de pantalla "Pantalla principal"). Es la pantalla de entrada habitual
 * de la app una vez existe al menos un mes creado.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0012\u0010\u0011\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/warthogcash/presupuesto/ui/main/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/warthogcash/presupuesto/ui/common/CategoriaAdapter;", "binding", "Lcom/warthogcash/presupuesto/databinding/ActivityMainBinding;", "viewModel", "Lcom/warthogcash/presupuesto/ui/main/MainViewModel;", "getViewModel", "()Lcom/warthogcash/presupuesto/ui/main/MainViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "abrirHistorialDeCategoria", "", "categoria", "Lcom/warthogcash/presupuesto/domain/model/Categoria;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.warthogcash.presupuesto.databinding.ActivityMainBinding binding;
    private com.warthogcash.presupuesto.ui.common.CategoriaAdapter adapter;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    
    public MainActivity() {
        super();
    }
    
    private final com.warthogcash.presupuesto.ui.main.MainViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void abrirHistorialDeCategoria(com.warthogcash.presupuesto.domain.model.Categoria categoria) {
    }
}