package com.warthogcash.presupuesto.data;

/**
 * Instancia central de la base de datos Room (especificación técnica, 5.4).
 * Estrategia de migraciones pendiente de definir (sección 8); de momento,
 * al no haber versiones publicadas, no se requiere ninguna.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/warthogcash/presupuesto/data/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "categoriaDao", "Lcom/warthogcash/presupuesto/data/dao/CategoriaDao;", "gastoDao", "Lcom/warthogcash/presupuesto/data/dao/GastoDao;", "presupuestoDao", "Lcom/warthogcash/presupuesto/data/dao/PresupuestoDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.warthogcash.presupuesto.data.entity.PresupuestoEntity.class, com.warthogcash.presupuesto.data.entity.CategoriaEntity.class, com.warthogcash.presupuesto.data.entity.GastoEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.warthogcash.presupuesto.data.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.warthogcash.presupuesto.data.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.warthogcash.presupuesto.data.dao.PresupuestoDao presupuestoDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.warthogcash.presupuesto.data.dao.CategoriaDao categoriaDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.warthogcash.presupuesto.data.dao.GastoDao gastoDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/warthogcash/presupuesto/data/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/warthogcash/presupuesto/data/AppDatabase;", "obtenerInstancia", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.warthogcash.presupuesto.data.AppDatabase obtenerInstancia(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}