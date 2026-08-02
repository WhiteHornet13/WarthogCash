package com.warthogcash.presupuesto.data.entity;

/**
 * Un mes concreto: mes, año, dinero disponible, estado (abierto/cerrado).
 * Especificación técnica, sección 5.1.
 *
 * [esActual] marca cuál es el mes "actual" de la app (a lo sumo uno true
 * a la vez). No aparece nombrado como campo propio en la especificación
 * técnica, pero es necesario para implementar la regla de negocio de
 * "mes actual" descrita repetidamente en las especificaciones de pantalla
 * (Pantalla principal 4.6, Mis meses 4.4/4.6).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0087\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\fH\u00c6\u0003JE\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001J\u0013\u0010 \u001a\u00020\f2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00d6\u0001J\t\u0010#\u001a\u00020\nH\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f\u00a8\u0006$"}, d2 = {"Lcom/warthogcash/presupuesto/data/entity/PresupuestoEntity;", "", "id", "", "mes", "", "anio", "dineroDisponible", "", "estado", "", "esActual", "", "(JIIDLjava/lang/String;Z)V", "getAnio", "()I", "getDineroDisponible", "()D", "getEsActual", "()Z", "getEstado", "()Ljava/lang/String;", "getId", "()J", "getMes", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "presupuestos")
public final class PresupuestoEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final int mes = 0;
    private final int anio = 0;
    private final double dineroDisponible = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String estado = null;
    private final boolean esActual = false;
    
    public PresupuestoEntity(long id, int mes, int anio, double dineroDisponible, @org.jetbrains.annotations.NotNull()
    java.lang.String estado, boolean esActual) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final int getMes() {
        return 0;
    }
    
    public final int getAnio() {
        return 0;
    }
    
    public final double getDineroDisponible() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEstado() {
        return null;
    }
    
    public final boolean getEsActual() {
        return false;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.data.entity.PresupuestoEntity copy(long id, int mes, int anio, double dineroDisponible, @org.jetbrains.annotations.NotNull()
    java.lang.String estado, boolean esActual) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}