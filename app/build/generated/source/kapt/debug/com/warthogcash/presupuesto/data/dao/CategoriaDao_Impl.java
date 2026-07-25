package com.warthogcash.presupuesto.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.warthogcash.presupuesto.data.entity.CategoriaEntity;
import java.lang.Class;
import java.lang.Exception;
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
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CategoriaDao_Impl implements CategoriaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CategoriaEntity> __insertionAdapterOfCategoriaEntity;

  public CategoriaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategoriaEntity = new EntityInsertionAdapter<CategoriaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `categorias` (`id`,`presupuestoId`,`tipo`,`porcentaje`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CategoriaEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPresupuestoId());
        if (entity.getTipo() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTipo());
        }
        statement.bindDouble(4, entity.getPorcentaje());
      }
    };
  }

  @Override
  public Object insertarTodas(final List<CategoriaEntity> categorias,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfCategoriaEntity.insertAndReturnIdsList(categorias);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerPorPresupuesto(final long presupuestoId,
      final Continuation<? super List<CategoriaEntity>> $completion) {
    final String _sql = "SELECT * FROM categorias WHERE presupuestoId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, presupuestoId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CategoriaEntity>>() {
      @Override
      @NonNull
      public List<CategoriaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPresupuestoId = CursorUtil.getColumnIndexOrThrow(_cursor, "presupuestoId");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfPorcentaje = CursorUtil.getColumnIndexOrThrow(_cursor, "porcentaje");
          final List<CategoriaEntity> _result = new ArrayList<CategoriaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoriaEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPresupuestoId;
            _tmpPresupuestoId = _cursor.getLong(_cursorIndexOfPresupuestoId);
            final String _tmpTipo;
            if (_cursor.isNull(_cursorIndexOfTipo)) {
              _tmpTipo = null;
            } else {
              _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            }
            final double _tmpPorcentaje;
            _tmpPorcentaje = _cursor.getDouble(_cursorIndexOfPorcentaje);
            _item = new CategoriaEntity(_tmpId,_tmpPresupuestoId,_tmpTipo,_tmpPorcentaje);
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
  public Flow<List<CategoriaEntity>> observarPorPresupuesto(final long presupuestoId) {
    final String _sql = "SELECT * FROM categorias WHERE presupuestoId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, presupuestoId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"categorias"}, new Callable<List<CategoriaEntity>>() {
      @Override
      @NonNull
      public List<CategoriaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPresupuestoId = CursorUtil.getColumnIndexOrThrow(_cursor, "presupuestoId");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfPorcentaje = CursorUtil.getColumnIndexOrThrow(_cursor, "porcentaje");
          final List<CategoriaEntity> _result = new ArrayList<CategoriaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoriaEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPresupuestoId;
            _tmpPresupuestoId = _cursor.getLong(_cursorIndexOfPresupuestoId);
            final String _tmpTipo;
            if (_cursor.isNull(_cursorIndexOfTipo)) {
              _tmpTipo = null;
            } else {
              _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            }
            final double _tmpPorcentaje;
            _tmpPorcentaje = _cursor.getDouble(_cursorIndexOfPorcentaje);
            _item = new CategoriaEntity(_tmpId,_tmpPresupuestoId,_tmpTipo,_tmpPorcentaje);
            _result.add(_item);
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
      final Continuation<? super CategoriaEntity> $completion) {
    final String _sql = "SELECT * FROM categorias WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CategoriaEntity>() {
      @Override
      @Nullable
      public CategoriaEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPresupuestoId = CursorUtil.getColumnIndexOrThrow(_cursor, "presupuestoId");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfPorcentaje = CursorUtil.getColumnIndexOrThrow(_cursor, "porcentaje");
          final CategoriaEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPresupuestoId;
            _tmpPresupuestoId = _cursor.getLong(_cursorIndexOfPresupuestoId);
            final String _tmpTipo;
            if (_cursor.isNull(_cursorIndexOfTipo)) {
              _tmpTipo = null;
            } else {
              _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            }
            final double _tmpPorcentaje;
            _tmpPorcentaje = _cursor.getDouble(_cursorIndexOfPorcentaje);
            _result = new CategoriaEntity(_tmpId,_tmpPresupuestoId,_tmpTipo,_tmpPorcentaje);
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
  public Object obtenerPorPresupuestoYTipo(final long presupuestoId, final String tipo,
      final Continuation<? super CategoriaEntity> $completion) {
    final String _sql = "SELECT * FROM categorias WHERE presupuestoId = ? AND tipo = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, presupuestoId);
    _argIndex = 2;
    if (tipo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tipo);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CategoriaEntity>() {
      @Override
      @Nullable
      public CategoriaEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPresupuestoId = CursorUtil.getColumnIndexOrThrow(_cursor, "presupuestoId");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final int _cursorIndexOfPorcentaje = CursorUtil.getColumnIndexOrThrow(_cursor, "porcentaje");
          final CategoriaEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPresupuestoId;
            _tmpPresupuestoId = _cursor.getLong(_cursorIndexOfPresupuestoId);
            final String _tmpTipo;
            if (_cursor.isNull(_cursorIndexOfTipo)) {
              _tmpTipo = null;
            } else {
              _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            }
            final double _tmpPorcentaje;
            _tmpPorcentaje = _cursor.getDouble(_cursorIndexOfPorcentaje);
            _result = new CategoriaEntity(_tmpId,_tmpPresupuestoId,_tmpTipo,_tmpPorcentaje);
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
