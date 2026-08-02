package com.warthogcash.presupuesto.domain.model;

/**
 * Un mes concreto de presupuesto: mes, año, dinero disponible, estado
 * (abierto/cerrado) y si es el mes "actual" (especificación técnica, 5.1).
 * Modelo de dominio: sin dependencia de Room, consumido por la UI.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0014\b\u0086\b\u0018\u0000 :2\u00020\u0001:\u0001:BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\u0002\u0010\u0010J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0005H\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\t\u00101\u001a\u00020\bH\u00c6\u0003J\t\u00102\u001a\u00020\nH\u00c6\u0003J\t\u00103\u001a\u00020\fH\u00c6\u0003J\u000f\u00104\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0003JU\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0001J\u0013\u00106\u001a\u00020\f2\b\u00107\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00108\u001a\u00020\u0005H\u00d6\u0001J\t\u00109\u001a\u00020#H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c8F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0012R\u0011\u0010\"\u001a\u00020#8F\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020\'8F\u00a2\u0006\u0006\u001a\u0004\b(\u0010)R\u0011\u0010*\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b+\u0010\u0016R\u0011\u0010,\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b-\u0010\u0016\u00a8\u0006;"}, d2 = {"Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "", "id", "", "mes", "", "anio", "dineroDisponible", "", "estado", "Lcom/warthogcash/presupuesto/domain/model/EstadoPresupuesto;", "esActual", "", "categorias", "", "Lcom/warthogcash/presupuesto/domain/model/Categoria;", "(JIIDLcom/warthogcash/presupuesto/domain/model/EstadoPresupuesto;ZLjava/util/List;)V", "getAnio", "()I", "getCategorias", "()Ljava/util/List;", "getDineroDisponible", "()D", "getEsActual", "()Z", "getEstado", "()Lcom/warthogcash/presupuesto/domain/model/EstadoPresupuesto;", "estadoBarra", "Lcom/warthogcash/presupuesto/domain/model/EstadoBarraProgreso;", "getEstadoBarra", "()Lcom/warthogcash/presupuesto/domain/model/EstadoBarraProgreso;", "getId", "()J", "getMes", "nombreMesAnio", "", "getNombreMesAnio", "()Ljava/lang/String;", "progresoTotal", "", "getProgresoTotal", "()F", "totalGastado", "getTotalGastado", "totalRestante", "getTotalRestante", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "Companion", "app_debug"})
public final class Presupuesto {
    private final long id = 0L;
    private final int mes = 0;
    private final int anio = 0;
    private final double dineroDisponible = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.domain.model.EstadoPresupuesto estado = null;
    private final boolean esActual = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.warthogcash.presupuesto.domain.model.Categoria> categorias = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> NOMBRES_MES = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.warthogcash.presupuesto.domain.model.Presupuesto.Companion Companion = null;
    
    public Presupuesto(long id, int mes, int anio, double dineroDisponible, @org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.model.EstadoPresupuesto estado, boolean esActual, @org.jetbrains.annotations.NotNull()
    java.util.List<com.warthogcash.presupuesto.domain.model.Categoria> categorias) {
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
    public final com.warthogcash.presupuesto.domain.model.EstadoPresupuesto getEstado() {
        return null;
    }
    
    public final boolean getEsActual() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.warthogcash.presupuesto.domain.model.Categoria> getCategorias() {
        return null;
    }
    
    public final double getTotalGastado() {
        return 0.0;
    }
    
    public final double getTotalRestante() {
        return 0.0;
    }
    
    public final float getProgresoTotal() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.domain.model.EstadoBarraProgreso getEstadoBarra() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNombreMesAnio() {
        return null;
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
    public final com.warthogcash.presupuesto.domain.model.EstadoPresupuesto component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.warthogcash.presupuesto.domain.model.Categoria> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.domain.model.Presupuesto copy(long id, int mes, int anio, double dineroDisponible, @org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.model.EstadoPresupuesto estado, boolean esActual, @org.jetbrains.annotations.NotNull()
    java.util.List<com.warthogcash.presupuesto.domain.model.Categoria> categorias) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/warthogcash/presupuesto/domain/model/Presupuesto$Companion;", "", "()V", "NOMBRES_MES", "", "", "getNOMBRES_MES", "()Ljava/util/List;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getNOMBRES_MES() {
            return null;
        }
    }
}