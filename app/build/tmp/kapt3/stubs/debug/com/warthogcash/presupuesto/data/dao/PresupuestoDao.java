package com.warthogcash.presupuesto.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0015\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0010\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0017H\'J\u0018\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00172\u0006\u0010\b\u001a\u00020\tH\'J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ$\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u001b2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0018\u0010\u001f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006 "}, d2 = {"Lcom/warthogcash/presupuesto/data/dao/PresupuestoDao;", "", "actualizar", "", "presupuesto", "Lcom/warthogcash/presupuesto/data/entity/PresupuestoEntity;", "(Lcom/warthogcash/presupuesto/data/entity/PresupuestoEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "actualizarEstado", "id", "", "estado", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "contarMeses", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "existeActualDistintoDe", "", "presupuestoId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertar", "limpiarActual", "observarActual", "Lkotlinx/coroutines/flow/Flow;", "observarPorId", "obtenerActual", "obtenerPagina", "", "limite", "offset", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerPorId", "app_debug"})
@androidx.room.Dao()
public abstract interface PresupuestoDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertar(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.data.entity.PresupuestoEntity presupuesto, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object actualizar(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.data.entity.PresupuestoEntity presupuesto, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM presupuestos")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarMeses(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM presupuestos WHERE esActual = 1 LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerActual(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.data.entity.PresupuestoEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM presupuestos WHERE esActual = 1 LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.warthogcash.presupuesto.data.entity.PresupuestoEntity> observarActual();
    
    @androidx.room.Query(value = "SELECT * FROM presupuestos WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorId(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.data.entity.PresupuestoEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM presupuestos WHERE id = :id")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.warthogcash.presupuesto.data.entity.PresupuestoEntity> observarPorId(long id);
    
    @androidx.room.Query(value = "UPDATE presupuestos SET esActual = 0 WHERE esActual = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object limpiarActual(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE presupuestos SET estado = :estado WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object actualizarEstado(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String estado, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM presupuestos ORDER BY anio DESC, mes DESC LIMIT :limite OFFSET :offset")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPagina(int limite, int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.data.entity.PresupuestoEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT EXISTS(SELECT 1 FROM presupuestos WHERE esActual = 1 AND id != :presupuestoId)")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object existeActualDistintoDe(long presupuestoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
}