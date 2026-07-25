package com.warthogcash.presupuesto.util;

/**
 * Formateo de importes y fechas coherente con los mockups (ej. "1.284,50 €").
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016J\u000e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/warthogcash/presupuesto/util/Formato;", "", "()V", "formatoFechaCorta", "Ljava/text/SimpleDateFormat;", "formatoMoneda", "Ljava/text/NumberFormat;", "getFormatoMoneda", "()Ljava/text/NumberFormat;", "formatoMoneda$delegate", "Lkotlin/Lazy;", "nombresMes", "", "", "fechaCorta", "epochMillis", "", "moneda", "valor", "", "nombreMes", "mes", "", "anio", "soloNombreMes", "app_debug"})
public final class Formato {
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy formatoMoneda$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> nombresMes = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat formatoFechaCorta = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.warthogcash.presupuesto.util.Formato INSTANCE = null;
    
    private Formato() {
        super();
    }
    
    private final java.text.NumberFormat getFormatoMoneda() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String moneda(double valor) {
        return null;
    }
    
    /**
     * "Julio 2026" a partir de mes (1-12) y año.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String nombreMes(int mes, int anio) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String soloNombreMes(int mes) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fechaCorta(long epochMillis) {
        return null;
    }
}