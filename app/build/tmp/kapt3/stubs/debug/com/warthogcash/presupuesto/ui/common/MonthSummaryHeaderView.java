package com.warthogcash.presupuesto.ui.common;

/**
 * Bloque reutilizable "Header / Resumen de mes", usado en Pantalla
 * principal, Detalle de mes anterior (abierto) y Detalle de mes cerrado
 * (especificación técnica, sección 7: componentes reutilizables entre
 * pantallas, no duplicados por pantalla).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0010J\u001c\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0010J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/warthogcash/presupuesto/ui/common/MonthSummaryHeaderView;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/warthogcash/presupuesto/databinding/ViewMonthSummaryHeaderBinding;", "mostrarComoDetalle", "", "presupuesto", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "alPulsarVolver", "Lkotlin/Function0;", "mostrarComoPantallaPrincipal", "alPulsarCalendario", "rellenarImportes", "app_debug"})
public final class MonthSummaryHeaderView extends android.widget.FrameLayout {
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.databinding.ViewMonthSummaryHeaderBinding binding = null;
    
    @kotlin.jvm.JvmOverloads()
    public MonthSummaryHeaderView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    /**
     * Variante para la Pantalla principal: sin flecha, con icono calendario, sin badge.
     */
    public final void mostrarComoPantallaPrincipal(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.model.Presupuesto presupuesto, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> alPulsarCalendario) {
    }
    
    /**
     * Variante para pantallas de detalle: con flecha + nombre de mes, badge de estado.
     */
    public final void mostrarComoDetalle(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.model.Presupuesto presupuesto, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> alPulsarVolver) {
    }
    
    private final void rellenarImportes(com.warthogcash.presupuesto.domain.model.Presupuesto presupuesto) {
    }
    
    @kotlin.jvm.JvmOverloads()
    public MonthSummaryHeaderView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public MonthSummaryHeaderView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
}