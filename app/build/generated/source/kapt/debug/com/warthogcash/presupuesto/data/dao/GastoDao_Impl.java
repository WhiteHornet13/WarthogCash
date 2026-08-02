package com.warthogcash.presupuesto.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.warthogcash.presupuesto.data.entity.GastoEntity;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GastoDao_Impl implements GastoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GastoEntity> __insertionAdapterOfGastoEntity;

  public GastoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGastoEntity = new EntityInsertionAdapter<GastoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `gastos` (`id`,`categoriaId`,`importe`,`descripcion`,`fecha`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GastoEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoriaId());
        statement.bindDouble(3, entity.getImporte());
        if (entity.getDescripcion() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescripcion());
        }
        statement.bindLong(5, entity.getFecha());
      }
    };
  }

  @Override
  public Object insertar(final GastoEntity gasto, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfGastoEntity.insertAndReturnId(gasto);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerPorCategoria(final long categoriaId,
      final Continuation<? super List<GastoEntity>> $completion) {
    final String _sql = "SELECT * FROM gastos WHERE categoriaId = ? ORDER BY fecha DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoriaId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<GastoEntity>>() {
      @Override
      @NonNull
      public List<GastoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoriaId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoriaId");
          final int _cursorIndexOfImporte = CursorUtil.getColumnIndexOrThrow(_cursor, "importe");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final List<GastoEntity> _result = new ArrayList<GastoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GastoEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoriaId;
            _tmpCategoriaId = _cursor.getLong(_cursorIndexOfCategoriaId);
            final double _tmpImporte;
            _tmpImporte = _cursor.getDouble(_cursorIndexOfImporte);
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final long _tmpFecha;
            _tmpFecha = _cursor.getLong(_cursorIndexOfFecha);
            _item = new GastoEntity(_tmpId,_tmpCategoriaId,_tmpImporte,_tmpDescripcion,_tmpFecha);
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
  public Object obtenerPorCategorias(final List<Long> categoriaIds,
      final Continuation<? super List<GastoEntity>> $completion) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM gastos WHERE categoriaId IN (");
    final int _inputSize = categoriaIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY fecha DESC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (Long _item : categoriaIds) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindLong(_argIndex, _item);
      }
      _argIndex++;
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<GastoEntity>>() {
      @Override
      @NonNull
      public List<GastoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoriaId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoriaId");
          final int _cursorIndexOfImporte = CursorUtil.getColumnIndexOrThrow(_cursor, "importe");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final List<GastoEntity> _result = new ArrayList<GastoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GastoEntity _item_1;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoriaId;
            _tmpCategoriaId = _cursor.getLong(_cursorIndexOfCategoriaId);
            final double _tmpImporte;
            _tmpImporte = _cursor.getDouble(_cursorIndexOfImporte);
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final long _tmpFecha;
            _tmpFecha = _cursor.getLong(_cursorIndexOfFecha);
            _item_1 = new GastoEntity(_tmpId,_tmpCategoriaId,_tmpImporte,_tmpDescripcion,_tmpFecha);
            _result.add(_item_1);
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
  public Flow<List<GastoEntity>> observarPorCategorias(final List<Long> categoriaIds) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM gastos WHERE categoriaId IN (");
    final int _inputSize = categoriaIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY fecha DESC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (Long _item : categoriaIds) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindLong(_argIndex, _item);
      }
      _argIndex++;
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"gastos"}, new Callable<List<GastoEntity>>() {
      @Override
      @NonNull
      public List<GastoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoriaId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoriaId");
          final int _cursorIndexOfImporte = CursorUtil.getColumnIndexOrThrow(_cursor, "importe");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfFecha = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha");
          final List<GastoEntity> _result = new ArrayList<GastoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GastoEntity _item_1;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoriaId;
            _tmpCategoriaId = _cursor.getLong(_cursorIndexOfCategoriaId);
            final double _tmpImporte;
            _tmpImporte = _cursor.getDouble(_cursorIndexOfImporte);
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final long _tmpFecha;
            _tmpFecha = _cursor.getLong(_cursorIndexOfFecha);
            _item_1 = new GastoEntity(_tmpId,_tmpCategoriaId,_tmpImporte,_tmpDescripcion,_tmpFecha);
            _result.add(_item_1);
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
  public Object sumarPorCategoria(final long categoriaId,
      final Continuation<? super Double> $completion) {
    final String _sql = "SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoriaId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
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
  public Object sumarPorCategorias(final List<Long> categoriaIds,
      final Continuation<? super Double> $completion) {
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT COALESCE(SUM(importe), 0) FROM gastos WHERE categoriaId IN (");
    final int _inputSize = categoriaIds.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (Long _item : categoriaIds) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindLong(_argIndex, _item);
      }
      _argIndex++;
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
