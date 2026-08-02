package com.warthogcash.presupuesto.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.warthogcash.presupuesto.data.dao.CategoriaDao;
import com.warthogcash.presupuesto.data.dao.CategoriaDao_Impl;
import com.warthogcash.presupuesto.data.dao.GastoDao;
import com.warthogcash.presupuesto.data.dao.GastoDao_Impl;
import com.warthogcash.presupuesto.data.dao.PresupuestoDao;
import com.warthogcash.presupuesto.data.dao.PresupuestoDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PresupuestoDao _presupuestoDao;

  private volatile CategoriaDao _categoriaDao;

  private volatile GastoDao _gastoDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `presupuestos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mes` INTEGER NOT NULL, `anio` INTEGER NOT NULL, `dineroDisponible` REAL NOT NULL, `estado` TEXT NOT NULL, `esActual` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categorias` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `presupuestoId` INTEGER NOT NULL, `tipo` TEXT NOT NULL, `porcentaje` REAL NOT NULL, FOREIGN KEY(`presupuestoId`) REFERENCES `presupuestos`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_categorias_presupuestoId` ON `categorias` (`presupuestoId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `gastos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `categoriaId` INTEGER NOT NULL, `importe` REAL NOT NULL, `descripcion` TEXT, `fecha` INTEGER NOT NULL, FOREIGN KEY(`categoriaId`) REFERENCES `categorias`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_gastos_categoriaId` ON `gastos` (`categoriaId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c31bdb2af1cf5a57628413fb0fd02783')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `presupuestos`");
        db.execSQL("DROP TABLE IF EXISTS `categorias`");
        db.execSQL("DROP TABLE IF EXISTS `gastos`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPresupuestos = new HashMap<String, TableInfo.Column>(6);
        _columnsPresupuestos.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresupuestos.put("mes", new TableInfo.Column("mes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresupuestos.put("anio", new TableInfo.Column("anio", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresupuestos.put("dineroDisponible", new TableInfo.Column("dineroDisponible", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresupuestos.put("estado", new TableInfo.Column("estado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPresupuestos.put("esActual", new TableInfo.Column("esActual", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPresupuestos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPresupuestos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPresupuestos = new TableInfo("presupuestos", _columnsPresupuestos, _foreignKeysPresupuestos, _indicesPresupuestos);
        final TableInfo _existingPresupuestos = TableInfo.read(db, "presupuestos");
        if (!_infoPresupuestos.equals(_existingPresupuestos)) {
          return new RoomOpenHelper.ValidationResult(false, "presupuestos(com.warthogcash.presupuesto.data.entity.PresupuestoEntity).\n"
                  + " Expected:\n" + _infoPresupuestos + "\n"
                  + " Found:\n" + _existingPresupuestos);
        }
        final HashMap<String, TableInfo.Column> _columnsCategorias = new HashMap<String, TableInfo.Column>(4);
        _columnsCategorias.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorias.put("presupuestoId", new TableInfo.Column("presupuestoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorias.put("tipo", new TableInfo.Column("tipo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategorias.put("porcentaje", new TableInfo.Column("porcentaje", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategorias = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCategorias.add(new TableInfo.ForeignKey("presupuestos", "CASCADE", "NO ACTION", Arrays.asList("presupuestoId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCategorias = new HashSet<TableInfo.Index>(1);
        _indicesCategorias.add(new TableInfo.Index("index_categorias_presupuestoId", false, Arrays.asList("presupuestoId"), Arrays.asList("ASC")));
        final TableInfo _infoCategorias = new TableInfo("categorias", _columnsCategorias, _foreignKeysCategorias, _indicesCategorias);
        final TableInfo _existingCategorias = TableInfo.read(db, "categorias");
        if (!_infoCategorias.equals(_existingCategorias)) {
          return new RoomOpenHelper.ValidationResult(false, "categorias(com.warthogcash.presupuesto.data.entity.CategoriaEntity).\n"
                  + " Expected:\n" + _infoCategorias + "\n"
                  + " Found:\n" + _existingCategorias);
        }
        final HashMap<String, TableInfo.Column> _columnsGastos = new HashMap<String, TableInfo.Column>(5);
        _columnsGastos.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("categoriaId", new TableInfo.Column("categoriaId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("importe", new TableInfo.Column("importe", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("descripcion", new TableInfo.Column("descripcion", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGastos.put("fecha", new TableInfo.Column("fecha", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGastos = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysGastos.add(new TableInfo.ForeignKey("categorias", "CASCADE", "NO ACTION", Arrays.asList("categoriaId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesGastos = new HashSet<TableInfo.Index>(1);
        _indicesGastos.add(new TableInfo.Index("index_gastos_categoriaId", false, Arrays.asList("categoriaId"), Arrays.asList("ASC")));
        final TableInfo _infoGastos = new TableInfo("gastos", _columnsGastos, _foreignKeysGastos, _indicesGastos);
        final TableInfo _existingGastos = TableInfo.read(db, "gastos");
        if (!_infoGastos.equals(_existingGastos)) {
          return new RoomOpenHelper.ValidationResult(false, "gastos(com.warthogcash.presupuesto.data.entity.GastoEntity).\n"
                  + " Expected:\n" + _infoGastos + "\n"
                  + " Found:\n" + _existingGastos);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "c31bdb2af1cf5a57628413fb0fd02783", "49cef2cdc96365c0dd7b43efcc22fca6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "presupuestos","categorias","gastos");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `presupuestos`");
      _db.execSQL("DELETE FROM `categorias`");
      _db.execSQL("DELETE FROM `gastos`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PresupuestoDao.class, PresupuestoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoriaDao.class, CategoriaDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GastoDao.class, GastoDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PresupuestoDao presupuestoDao() {
    if (_presupuestoDao != null) {
      return _presupuestoDao;
    } else {
      synchronized(this) {
        if(_presupuestoDao == null) {
          _presupuestoDao = new PresupuestoDao_Impl(this);
        }
        return _presupuestoDao;
      }
    }
  }

  @Override
  public CategoriaDao categoriaDao() {
    if (_categoriaDao != null) {
      return _categoriaDao;
    } else {
      synchronized(this) {
        if(_categoriaDao == null) {
          _categoriaDao = new CategoriaDao_Impl(this);
        }
        return _categoriaDao;
      }
    }
  }

  @Override
  public GastoDao gastoDao() {
    if (_gastoDao != null) {
      return _gastoDao;
    } else {
      synchronized(this) {
        if(_gastoDao == null) {
          _gastoDao = new GastoDao_Impl(this);
        }
        return _gastoDao;
      }
    }
  }
}
