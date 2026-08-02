package com.warthogcash.presupuesto.ui.addexpense;

/**
 * Especificación de pantalla "Añadir gasto". Solo accesible desde el
 * botón + de la Pantalla principal o del detalle de un mes anterior
 * abierto (sección 2); nunca desde un mes cerrado.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0010H\u0002J\u000f\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002\u00a2\u0006\u0002\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001b\u001a\u00020\u00102\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u001f"}, d2 = {"Lcom/warthogcash/presupuesto/ui/addexpense/AddExpenseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/warthogcash/presupuesto/databinding/ActivityAddExpenseBinding;", "categoriaSeleccionada", "Lcom/warthogcash/presupuesto/domain/model/Categoria;", "mesId", "", "viewModel", "Lcom/warthogcash/presupuesto/ui/addexpense/AddExpenseViewModel;", "getViewModel", "()Lcom/warthogcash/presupuesto/ui/addexpense/AddExpenseViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "actualizarVistaPreviaYBoton", "", "construirChips", "mes", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "guardarGasto", "importeIngresado", "", "()Ljava/lang/Double;", "ocultarTeclado", "vista", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
public final class AddExpenseActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_MES_ID = "extra_mes_id";
    private com.warthogcash.presupuesto.databinding.ActivityAddExpenseBinding binding;
    private long mesId = -1L;
    @org.jetbrains.annotations.Nullable()
    private com.warthogcash.presupuesto.domain.model.Categoria categoriaSeleccionada;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.warthogcash.presupuesto.ui.addexpense.AddExpenseActivity.Companion Companion = null;
    
    public AddExpenseActivity() {
        super();
    }
    
    private final com.warthogcash.presupuesto.ui.addexpense.AddExpenseViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void construirChips(com.warthogcash.presupuesto.domain.model.Presupuesto mes) {
    }
    
    private final void actualizarVistaPreviaYBoton() {
    }
    
    private final void ocultarTeclado(android.view.View vista) {
    }
    
    private final java.lang.Double importeIngresado() {
        return null;
    }
    
    private final void guardarGasto() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/warthogcash/presupuesto/ui/addexpense/AddExpenseActivity$Companion;", "", "()V", "EXTRA_MES_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}