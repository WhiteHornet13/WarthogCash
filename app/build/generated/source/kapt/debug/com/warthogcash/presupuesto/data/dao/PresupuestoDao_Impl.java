package com.warthogcash.presupuesto.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.warthogcash.presupuesto.data.entity.PresupuestoEntity;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PresupuestoDao_Impl implements PresupuestoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PresupuestoEntity> __insertionAdapterOfPresupuestoEntity;

  private final EntityDeletionOrUpdateAdapter<PresupuestoEntity> __updateAdapterOfPresupuestoEntity;

  private final SharedSQLiteStatement __preparedStmtOfLimpiarActual;

  private final SharedSQLiteStatement __preparedStmtOfActualizarEstado;

  public PresupuestoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPresupuestoEntity = new EntityInsertionAdapter<PresupuestoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `presupuestos` (`id`,`mes`,`anio`,`dineroDisponible`,`estado`,`esActual`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PresupuestoEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMes());
        statement.bindLong(3, entity.getAnio());
        statement.bindDouble(4, entity.getDineroDisponible());
        if (entity.getEstado() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEstado());
        }
        final int _tmp = entity.getEsActual() ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__updateAdapterOfPresupuestoEntity = new EntityDeletionOrUpdateAdapter<PresupuestoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `presupuestos` SET `id` = ?,`mes` = ?,`anio` = ?,`dineroDisponible` = ?,`estado` = ?,`esActual` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PresupuestoEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMes());
        statement.bindLong(3, entity.getAnio());
        statement.bindDouble(4, entity.getDineroDisponible());
        if (entity.getEstado() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEstado());
        }
        final int _tmp = entity.getEsActual() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfLimpiarActual = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE presupuestos SET esActual = 0 WHERE esActual = 1";
        return _query;
      }
    };
    this.__preparedStmtOfActualizarEstado = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE presupuestos SET estado = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertar(final PresupuestoEntity presupuesto,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPresupuestoEntity.insertAndReturnId(presupuesto);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object actualizar(final PresupuestoEntity presupuesto,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPresupuestoEntity.handle(presupuesto);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object limpiarActual(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfLimpiarActual.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfLimpiarActual.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object actualizarEstado(final long id, final String estado,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfActualizarEstado.acquire();
        int _argIndex = 1;
        if (estado == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, estado);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfActualizarEstado.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object contarMeses(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM presupuestos";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerActual(final Continuation<? super PresupuestoEntity> $completion) {
    final String _sql = "SELECT * FROM presupuestos WHERE esActual = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PresupuestoEntity>() {
      @Override
      @Nullable
      public PresupuestoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMes = CursorUtil.getColumnIndexOrThrow(_cursor, "mes");
          final int _cursorIndexOfAnio = CursorUtil.getColumnIndexOrThrow(_cursor, "anio");
          final int _cursorIndexOfDineroDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "dineroDisponible");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfEsActual = CursorUtil.getColumnIndexOrThrow(_cursor, "esActual");
          final PresupuestoEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMes;
            _tmpMes = _cursor.getInt(_cursorIndexOfMes);
            final int _tmpAnio;
            _tmpAnio = _cursor.getInt(_cursorIndexOfAnio);
            final double _tmpDineroDisponible;
            _tmpDineroDisponible = _cursor.getDouble(_cursorIndexOfDineroDisponible);
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            final boolean _tmpEsActual;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsActual);
            _tmpEsActual = _tmp != 0;
            _result = new PresupuestoEntity(_tmpId,_tmpMes,_tmpAnio,_tmpDineroDisponible,_tmpEstado,_tmpEsActual);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<PresupuestoEntity> observarActual() {
    final String _sql = "SELECT * FROM presupuestos WHERE esActual = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"presupuestos"}, new Callable<PresupuestoEntity>() {
      @Override
      @Nullable
      public PresupuestoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMes = CursorUtil.getColumnIndexOrThrow(_cursor, "mes");
          final int _cursorIndexOfAnio = CursorUtil.getColumnIndexOrThrow(_cursor, "anio");
          final int _cursorIndexOfDineroDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "dineroDisponible");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfEsActual = CursorUtil.getColumnIndexOrThrow(_cursor, "esActual");
          final PresupuestoEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMes;
            _tmpMes = _cursor.getInt(_cursorIndexOfMes);
            final int _tmpAnio;
            _tmpAnio = _cursor.getInt(_cursorIndexOfAnio);
            final double _tmpDineroDisponible;
            _tmpDineroDisponible = _cursor.getDouble(_cursorIndexOfDineroDisponible);
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            final boolean _tmpEsActual;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsActual);
            _tmpEsActual = _tmp != 0;
            _result = new PresupuestoEntity(_tmpId,_tmpMes,_tmpAnio,_tmpDineroDisponible,_tmpEstado,_tmpEsActual);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object obtenerPorId(final long id,
      final Continuation<? super PresupuestoEntity> $completion) {
    final String _sql = "SELECT * FROM presupuestos WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PresupuestoEntity>() {
      @Override
      @Nullable
      public PresupuestoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMes = CursorUtil.getColumnIndexOrThrow(_cursor, "mes");
          final int _cursorIndexOfAnio = CursorUtil.getColumnIndexOrThrow(_cursor, "anio");
          final int _cursorIndexOfDineroDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "dineroDisponible");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfEsActual = CursorUtil.getColumnIndexOrThrow(_cursor, "esActual");
          final PresupuestoEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMes;
            _tmpMes = _cursor.getInt(_cursorIndexOfMes);
            final int _tmpAnio;
            _tmpAnio = _cursor.getInt(_cursorIndexOfAnio);
            final double _tmpDineroDisponible;
            _tmpDineroDisponible = _cursor.getDouble(_cursorIndexOfDineroDisponible);
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            final boolean _tmpEsActual;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsActual);
            _tmpEsActual = _tmp != 0;
            _result = new PresupuestoEntity(_tmpId,_tmpMes,_tmpAnio,_tmpDineroDisponible,_tmpEstado,_tmpEsActual);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<PresupuestoEntity> observarPorId(final long id) {
    final String _sql = "SELECT * FROM presupuestos WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"presupuestos"}, new Callable<PresupuestoEntity>() {
      @Override
      @Nullable
      public PresupuestoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMes = CursorUtil.getColumnIndexOrThrow(_cursor, "mes");
          final int _cursorIndexOfAnio = CursorUtil.getColumnIndexOrThrow(_cursor, "anio");
          final int _cursorIndexOfDineroDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "dineroDisponible");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfEsActual = CursorUtil.getColumnIndexOrThrow(_cursor, "esActual");
          final PresupuestoEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMes;
            _tmpMes = _cursor.getInt(_cursorIndexOfMes);
            final int _tmpAnio;
            _tmpAnio = _cursor.getInt(_cursorIndexOfAnio);
            final double _tmpDineroDisponible;
            _tmpDineroDisponible = _cursor.getDouble(_cursorIndexOfDineroDisponible);
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            final boolean _tmpEsActual;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsActual);
            _tmpEsActual = _tmp != 0;
            _result = new PresupuestoEntity(_tmpId,_tmpMes,_tmpAnio,_tmpDineroDisponible,_tmpEstado,_tmpEsActual);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object obtenerPagina(final int limite, final int offset,
      final Continuation<? super List<PresupuestoEntity>> $completion) {
    final String _sql = "SELECT * FROM presupuestos ORDER BY anio DESC, mes DESC LIMIT ? OFFSET ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limite);
    _argIndex = 2;
    _statement.bindLong(_argIndex, offset);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PresupuestoEntity>>() {
      @Override
      @NonNull
      public List<PresupuestoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMes = CursorUtil.getColumnIndexOrThrow(_cursor, "mes");
          final int _cursorIndexOfAnio = CursorUtil.getColumnIndexOrThrow(_cursor, "anio");
          final int _cursorIndexOfDineroDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "dineroDisponible");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfEsActual = CursorUtil.getColumnIndexOrThrow(_cursor, "esActual");
          final List<PresupuestoEntity> _result = new ArrayList<PresupuestoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PresupuestoEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMes;
            _tmpMes = _cursor.getInt(_cursorIndexOfMes);
            final int _tmpAnio;
            _tmpAnio = _cursor.getInt(_cursorIndexOfAnio);
            final double _tmpDineroDisponible;
            _tmpDineroDisponible = _cursor.getDouble(_cursorIndexOfDineroDisponible);
            final String _tmpEstado;
            if (_cursor.isNull(_cursorIndexOfEstado)) {
              _tmpEstado = null;
            } else {
              _tmpEstado = _cursor.getString(_cursorIndexOfEstado);
            }
            final boolean _tmpEsActual;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsActual);
            _tmpEsActual = _tmp != 0;
            _item = new PresupuestoEntity(_tmpId,_tmpMes,_tmpAnio,_tmpDineroDisponible,_tmpEstado,_tmpEsActual);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object existeActualDistintoDe(final long presupuestoId,
      final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM presupuestos WHERE esActual = 1 AND id != ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, presupuestoId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
