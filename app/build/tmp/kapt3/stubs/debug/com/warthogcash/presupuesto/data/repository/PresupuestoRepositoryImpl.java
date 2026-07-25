package com.warthogcash.presupuesto.data.repository;

/**
 * Implementación del repositorio: traduce entre entidades Room y modelos
 * de dominio (especificación técnica, sección 5.4). Es la única capa que
 * conoce simultáneamente Room y el dominio.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ(\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u0014J:\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\r2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\r0\u001bH\u0096@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010\u001e\u001a\u00020\u001fH\u0096@\u00a2\u0006\u0002\u0010 J\u0016\u0010!\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0082@\u00a2\u0006\u0002\u0010&J\u0010\u0010\'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010#0(H\u0016J\u0018\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010#0(2\u0006\u0010*\u001a\u00020\nH\u0016J\u001c\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,2\u0006\u0010\u0013\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u0014J$\u0010.\u001a\b\u0012\u0004\u0012\u00020-0,2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010/\u001a\u00020\u001cH\u0096@\u00a2\u0006\u0002\u00100J\u0010\u00101\u001a\u0004\u0018\u00010#H\u0096@\u00a2\u0006\u0002\u0010 J\u0018\u00102\u001a\u0004\u0018\u00010#2\u0006\u0010*\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u0014J$\u00103\u001a\b\u0012\u0004\u0012\u00020#0,2\u0006\u00104\u001a\u00020\u00172\u0006\u00105\u001a\u00020\u0017H\u0096@\u00a2\u0006\u0002\u00106J\u001c\u00107\u001a\u000208*\u0002092\u0006\u0010:\u001a\u00020\r2\u0006\u0010;\u001a\u00020\rH\u0002J\f\u00107\u001a\u00020<*\u00020=H\u0002J\u001a\u00107\u001a\u00020#*\u00020%2\f\u0010>\u001a\b\u0012\u0004\u0012\u0002080,H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lcom/warthogcash/presupuesto/data/repository/PresupuestoRepositoryImpl;", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "presupuestoDao", "Lcom/warthogcash/presupuesto/data/dao/PresupuestoDao;", "categoriaDao", "Lcom/warthogcash/presupuesto/data/dao/CategoriaDao;", "gastoDao", "Lcom/warthogcash/presupuesto/data/dao/GastoDao;", "(Lcom/warthogcash/presupuesto/data/dao/PresupuestoDao;Lcom/warthogcash/presupuesto/data/dao/CategoriaDao;Lcom/warthogcash/presupuesto/data/dao/GastoDao;)V", "agregarGasto", "", "categoriaId", "importe", "", "descripcion", "", "(JDLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cerrarMes", "", "presupuestoId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "crearMes", "mes", "", "anio", "dineroDisponible", "porcentajes", "", "Lcom/warthogcash/presupuesto/domain/model/TipoCategoria;", "(IIDLjava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "existeAlgunMes", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "existeMesActualDistintoDe", "mapearPresupuestoCompleto", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "entidad", "Lcom/warthogcash/presupuesto/data/entity/PresupuestoEntity;", "(Lcom/warthogcash/presupuesto/data/entity/PresupuestoEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observarMesActual", "Lkotlinx/coroutines/flow/Flow;", "observarMesPorId", "id", "obtenerGastosDeMes", "", "Lcom/warthogcash/presupuesto/domain/model/GastoDetallado;", "obtenerGastosDeMesFiltrados", "tipo", "(JLcom/warthogcash/presupuesto/domain/model/TipoCategoria;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerMesActual", "obtenerMesPorId", "obtenerPaginaMeses", "limite", "offset", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "aDominio", "Lcom/warthogcash/presupuesto/domain/model/Categoria;", "Lcom/warthogcash/presupuesto/data/entity/CategoriaEntity;", "dineroDisponibleMes", "gastado", "Lcom/warthogcash/presupuesto/domain/model/Gasto;", "Lcom/warthogcash/presupuesto/data/entity/GastoEntity;", "categorias", "app_debug"})
public final class PresupuestoRepositoryImpl implements com.warthogcash.presupuesto.domain.repository.PresupuestoRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.data.dao.PresupuestoDao presupuestoDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.data.dao.CategoriaDao categoriaDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.warthogcash.presupuesto.data.dao.GastoDao gastoDao = null;
    
    public PresupuestoRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.data.dao.PresupuestoDao presupuestoDao, @org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.data.dao.CategoriaDao categoriaDao, @org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.data.dao.GastoDao gastoDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object existeAlgunMes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object obtenerMesActual(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.domain.model.Presupuesto> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.warthogcash.presupuesto.domain.model.Presupuesto> observarMesActual() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object obtenerMesPorId(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.domain.model.Presupuesto> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.warthogcash.presupuesto.domain.model.Presupuesto> observarMesPorId(long id) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object obtenerPaginaMeses(int limite, int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.domain.model.Presupuesto>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object crearMes(int mes, int anio, double dineroDisponible, @org.jetbrains.annotations.NotNull()
    java.util.Map<com.warthogcash.presupuesto.domain.model.TipoCategoria, java.lang.Double> porcentajes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object cerrarMes(long presupuestoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object agregarGasto(long categoriaId, double importe, @org.jetbrains.annotations.Nullable()
    java.lang.String descripcion, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object obtenerGastosDeMes(long presupuestoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object obtenerGastosDeMesFiltrados(long presupuestoId, @org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.model.TipoCategoria tipo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object existeMesActualDistintoDe(long presupuestoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.Object mapearPresupuestoCompleto(com.warthogcash.presupuesto.data.entity.PresupuestoEntity entidad, kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.domain.model.Presupuesto> $completion) {
        return null;
    }
    
    private final com.warthogcash.presupuesto.domain.model.Presupuesto aDominio(com.warthogcash.presupuesto.data.entity.PresupuestoEntity $this$aDominio, java.util.List<com.warthogcash.presupuesto.domain.model.Categoria> categorias) {
        return null;
    }
    
    private final com.warthogcash.presupuesto.domain.model.Categoria aDominio(com.warthogcash.presupuesto.data.entity.CategoriaEntity $this$aDominio, double dineroDisponibleMes, double gastado) {
        return null;
    }
    
    private final com.warthogcash.presupuesto.domain.model.Gasto aDominio(com.warthogcash.presupuesto.data.entity.GastoEntity $this$aDominio) {
        return null;
    }
}