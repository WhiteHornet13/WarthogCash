package com.warthogcash.presupuesto.data.entity;

/**
 * Un gasto individual, ligado a una Categoria. Especificación técnica,
 * sección 5.1. Los importes "gastado"/"restante" por categoría NO se
 * almacenan (sección 5.3); se derivan sumando estas filas.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B1\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J=\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001f"}, d2 = {"Lcom/warthogcash/presupuesto/data/entity/GastoEntity;", "", "id", "", "categoriaId", "importe", "", "descripcion", "", "fecha", "(JJDLjava/lang/String;J)V", "getCategoriaId", "()J", "getDescripcion", "()Ljava/lang/String;", "getFecha", "getId", "getImporte", "()D", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "gastos", foreignKeys = {@androidx.room.ForeignKey(entity = com.warthogcash.presupuesto.data.entity.CategoriaEntity.class, parentColumns = {"id"}, childColumns = {"categoriaId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"categoriaId"})})
public final class GastoEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long categoriaId = 0L;
    private final double importe = 0.0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String descripcion = null;
    private final long fecha = 0L;
    
    public GastoEntity(long id, long categoriaId, double importe, @org.jetbrains.annotations.Nullable()
    java.lang.String descripcion, long fecha) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getCategoriaId() {
        return 0L;
    }
    
    public final double getImporte() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDescripcion() {
        return null;
    }
    
    public final long getFecha() {
        return 0L;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    public final long component5() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.data.entity.GastoEntity copy(long id, long categoriaId, double importe, @org.jetbrains.annotations.Nullable()
    java.lang.String descripcion, long fecha) {
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