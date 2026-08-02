package com.warthogcash.presupuesto.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\'J\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\f\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\rJ\"\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u0012\u001a\u00020\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\tH\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0013"}, d2 = {"Lcom/warthogcash/presupuesto/data/dao/GastoDao;", "", "insertar", "", "gasto", "Lcom/warthogcash/presupuesto/data/entity/GastoEntity;", "(Lcom/warthogcash/presupuesto/data/entity/GastoEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observarPorCategorias", "Lkotlinx/coroutines/flow/Flow;", "", "categoriaIds", "obtenerPorCategoria", "categoriaId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerPorCategorias", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sumarPorCategoria", "", "sumarPorCategorias", "app_debug"})
@androidx.room.Dao()
public abstract interface GastoDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertar(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.data.entity.GastoEntity gasto, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM gastos WHERE categoriaId = :categoriaId ORDER BY fecha DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorCategoria(long categoriaId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.data.entity.GastoEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM gastos WHERE categoriaId IN (:categoriaIds) ORDER BY fecha DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorCategorias(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> categoriaIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.data.entity.GastoEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM gastos WHERE categoriaId IN (:categoriaIds) ORDER BY fecha DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.warthogcash.presupuesto.data.entity.GastoEntity>> observarPorCategorias(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> categoriaIds);
    
    @androidx.room.Query(value = "SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId = :categoriaId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sumarPorCategoria(long categoriaId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
    
    @androidx.room.Query(value = "SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId IN (:categoriaIds)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sumarPorCategorias(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> categoriaIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
}