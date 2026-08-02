package com.warthogcash.presupuesto.domain.repository;

/**
 * Contrato del repositorio de presupuestos. La interfaz de usuario nunca
 * depende directamente de Room; solo conoce esta interfaz
 * (especificación técnica, sección 5.4).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00a6@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\rJ:\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00062\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00060\u0014H\u00a6@\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\u00020\u0018H\u00a6@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\rJ\u0010\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\u001cH&J\u0018\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\u001c2\u0006\u0010\u001f\u001a\u00020\u0003H&J\u001c\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!2\u0006\u0010\f\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\rJ$\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0!2\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u0015H\u00a6@\u00a2\u0006\u0002\u0010%J\u0010\u0010&\u001a\u0004\u0018\u00010\u001dH\u00a6@\u00a2\u0006\u0002\u0010\u0019J\u0018\u0010\'\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001f\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\rJ$\u0010(\u001a\b\u0012\u0004\u0012\u00020\u001d0!2\u0006\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u0010H\u00a6@\u00a2\u0006\u0002\u0010+\u00a8\u0006,"}, d2 = {"Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "", "agregarGasto", "", "categoriaId", "importe", "", "descripcion", "", "(JDLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cerrarMes", "", "presupuestoId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "crearMes", "mes", "", "anio", "dineroDisponible", "porcentajes", "", "Lcom/warthogcash/presupuesto/domain/model/TipoCategoria;", "(IIDLjava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "existeAlgunMes", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "existeMesActualDistintoDe", "observarMesActual", "Lkotlinx/coroutines/flow/Flow;", "Lcom/warthogcash/presupuesto/domain/model/Presupuesto;", "observarMesPorId", "id", "obtenerGastosDeMes", "", "Lcom/warthogcash/presupuesto/domain/model/GastoDetallado;", "obtenerGastosDeMesFiltrados", "tipo", "(JLcom/warthogcash/presupuesto/domain/model/TipoCategoria;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerMesActual", "obtenerMesPorId", "obtenerPaginaMeses", "limite", "offset", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface PresupuestoRepository {
    
    /**
     * true si no existe ningún mes creado todavía (condición de la pantalla Bienvenida).
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object existeAlgunMes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerMesActual(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.domain.model.Presupuesto> $completion);
    
    /**
     * Observa cambios en el mes actual (usado por la Pantalla principal).
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.warthogcash.presupuesto.domain.model.Presupuesto> observarMesActual();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerMesPorId(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.warthogcash.presupuesto.domain.model.Presupuesto> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.warthogcash.presupuesto.domain.model.Presupuesto> observarMesPorId(long id);
    
    /**
     * Página de meses ordenados de más reciente a más antiguo, para "Mis meses".
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPaginaMeses(int limite, int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.domain.model.Presupuesto>> $completion);
    
    /**
     * Crea un nuevo mes con sus 5 categorías fijas y sus porcentajes.
     * El nuevo mes pasa a ser el "actual"; el mes que lo era hasta ahora
     * permanece abierto (especificación "Pantalla principal", sección 4.6).
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object crearMes(int mes, int anio, double dineroDisponible, @org.jetbrains.annotations.NotNull()
    java.util.Map<com.warthogcash.presupuesto.domain.model.TipoCategoria, java.lang.Double> porcentajes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Cierra un mes (no aplicable al mes actual, ver spec "Mis meses" 4.6).
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object cerrarMes(long presupuestoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Registra un gasto en una categoría; devuelve el id generado.
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object agregarGasto(long categoriaId, double importe, @org.jetbrains.annotations.Nullable()
    java.lang.String descripcion, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerGastosDeMes(long presupuestoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerGastosDeMesFiltrados(long presupuestoId, @org.jetbrains.annotations.NotNull()
    com.warthogcash.presupuesto.domain.model.TipoCategoria tipo, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.warthogcash.presupuesto.domain.model.GastoDetallado>> $completion);
    
    /**
     * true si existe un mes "actual" distinto del indicado (spec detalle mes anterior, 4.3).
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object existeMesActualDistintoDe(long presupuestoId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
}