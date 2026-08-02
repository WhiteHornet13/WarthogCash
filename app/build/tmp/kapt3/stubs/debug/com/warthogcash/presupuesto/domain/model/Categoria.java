package com.warthogcash.presupuesto.domain.model;

/**
 * Una de las 5 categorías fijas dentro de un Presupuesto (mes).
 *
 * Especificación técnica, sección 5.3: los importes "gastado" y "restante"
 * no se almacenan como campos propios; se calculan a partir de la suma de
 * los Gasto asociados, para evitar datos duplicados o desincronizados.
 * Por eso [gastado] llega ya calculado desde el repositorio y no desde Room.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\"\u001a\u00020\bH\u00c6\u0003J\t\u0010#\u001a\u00020\bH\u00c6\u0003J\t\u0010$\u001a\u00020\bH\u00c6\u0003JE\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\bH\u00c6\u0001J\u0013\u0010&\u001a\u00020\'2\b\u0010(\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010)\u001a\u00020*H\u00d6\u0001J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0011\u0010\f\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\u0017\u001a\u00020\u00188F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006-"}, d2 = {"Lcom/warthogcash/presupuesto/domain/model/Categoria;", "", "id", "", "presupuestoId", "tipo", "Lcom/warthogcash/presupuesto/domain/model/TipoCategoria;", "porcentaje", "", "montoAsignado", "gastado", "(JJLcom/warthogcash/presupuesto/domain/model/TipoCategoria;DDD)V", "estado", "Lcom/warthogcash/presupuesto/domain/model/EstadoBarraProgreso;", "getEstado", "()Lcom/warthogcash/presupuesto/domain/model/EstadoBarraProgreso;", "getGastado", "()D", "getId", "()J", "getMontoAsignado", "getPorcentaje", "getPresupuestoId", "progreso", "", "getProgreso", "()F", "restante", "getRestante", "getTipo", "()Lcom/warthogcash/presupuesto/domain/model/TipoCategoria;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class Categoria {
    private final long id = 0L;
    private final long presupuestoId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.domain.model.TipoCategoria tipo = null;
    private final double porcentaje = 0.0;
    private final double montoAsignado = 0.0;
    private final double gastado = 0.0;
    
    public Categoria(long id, long presupuestoId, @org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.model.TipoCategoria tipo, double porcentaje, double montoAsignado, double gastado) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getPresupuestoId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.domain.model.TipoCategoria getTipo() {
        return null;
    }
    
    public final double getPorcentaje() {
        return 0.0;
    }
    
    public final double getMontoAsignado() {
        return 0.0;
    }
    
    public final double getGastado() {
        return 0.0;
    }
    
    public final double getRestante() {
        return 0.0;
    }
    
    public final float getProgreso() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.domain.model.EstadoBarraProgreso getEstado() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.domain.model.TipoCategoria component3() {
        return null;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.domain.model.Categoria copy(long id, long presupuestoId, @org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.model.TipoCategoria tipo, double porcentaje, double montoAsignado, double gastado) {
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