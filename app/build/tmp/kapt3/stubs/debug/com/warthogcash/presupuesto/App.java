package com.warthogcash.presupuesto;

/**
 * Punto único de instanciación manual de dependencias.
 *
 * La especificación técnica deja pendiente (sección 8) si se usará un
 * framework de inyección de dependencias o instanciación manual. Se opta
 * aquí por instanciación manual mediante esta clase Application a modo de
 * contenedor simple, por ser la opción de menor complejidad para el
 * alcance actual del proyecto. Revisar si se decide adoptar Hilt/Koin
 * más adelante.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2 = {"Lcom/warthogcash/presupuesto/App;", "Landroid/app/Application;", "()V", "repository", "Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "getRepository", "()Lcom/warthogcash/presupuesto/domain/repository/PresupuestoRepository;", "repository$delegate", "Lkotlin/Lazy;", "app_debug"})
public final class App extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy repository$delegate = null;
    
    public App() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.warthogcash.presupuesto.domain.repository.PresupuestoRepository getRepository() {
        return null;
    }
}