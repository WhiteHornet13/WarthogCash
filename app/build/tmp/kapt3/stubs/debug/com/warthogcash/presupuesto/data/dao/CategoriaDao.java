package com.warthogcash.presupuesto.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00030\t2\u0006\u0010\n\u001a\u00020\u0004H\'J\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0006\u0010\n\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\rJ \u0010\u000f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/warthogcash/presupuesto/data/dao/CategoriaDao;", "", "insertarTodas", "", "", "categorias", "Lcom/warthogcash/presupuesto/data/entity/CategoriaEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observarPorPresupuesto", "Lkotlinx/coroutines/flow/Flow;", "presupuestoId", "obtenerPorId", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerPorPresupuesto", "obtenerPorPresupuestoYTipo", "tipo", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface CategoriaDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertarTodas(@org.jetbrains.annotations.NotNull()
    java.util.List<com.warthogcash.presupuesto.data.entity.CategoriaEntity> categorias, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM categorias WHERE presupuestoId = :presupuestoId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorPresupuesto(long presupuestoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.data.entity.CategoriaEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM categorias WHERE presupuestoId = :presupuestoId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.warthogcash.presupuesto.data.entity.CategoriaEntity>> observarPorPresupuesto(long presupuestoId);
    
    @androidx.room.Query(value = "SELECT * FROM categorias WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorId(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.data.entity.CategoriaEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM categorias WHERE presupuestoId = :presupuestoId AND tipo = :tipo LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorPresupuestoYTipo(long presupuestoId, @org.jetbrains.annotations.NotNull()
    java.lang.String tipo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.data.entity.CategoriaEntity> $completion);
}