package com.warthogcash.presupuesto.data.entity;

/**
 * Una de las 5 categorías fijas dentro de un Presupuesto: tipo (enum),
 * porcentaje asignado. Especificación técnica, sección 5.1/5.2.
 * El nombre de categoría (tipo) se guarda como texto (nombre del enum
 * [com.warthogcash.presupuesto.domain.model.TipoCategoria]), nunca como
 * catálogo editable en base de datos.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\'\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0006H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001c"}, d2 = {"Lcom/warthogcash/presupuesto/data/entity/CategoriaEntity;", "", "id", "", "presupuestoId", "tipo", "", "porcentaje", "", "(JJLjava/lang/String;D)V", "getId", "()J", "getPorcentaje", "()D", "getPresupuestoId", "getTipo", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "categorias", foreignKeys = {@androidx.room.ForeignKey(entity = com.warthogcash.presupuesto.data.entity.PresupuestoEntity.class, parentColumns = {"id"}, childColumns = {"presupuestoId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"presupuestoId"})})
public final class CategoriaEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long presupuestoId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tipo = null;
    private final double porcentaje = 0.0;
    
    public CategoriaEntity(long id, long presupuestoId, @org.jetbrains.annotations.NotNull()
    java.lang.String tipo, double porcentaje) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getPresupuestoId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTipo() {
        return null;
    }
    
    public final double getPorcentaje() {
        return 0.0;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.data.entity.CategoriaEntity copy(long id, long presupuestoId, @org.jetbrains.annotations.NotNull()
    java.lang.String tipo, double porcentaje) {
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