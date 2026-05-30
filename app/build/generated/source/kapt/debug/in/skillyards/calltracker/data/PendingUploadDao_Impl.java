package in.skillyards.calltracker.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
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
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PendingUploadDao_Impl implements PendingUploadDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PendingUploadEntity> __insertionAdapterOfPendingUploadEntity;

  private final EntityDeletionOrUpdateAdapter<PendingUploadEntity> __deletionAdapterOfPendingUploadEntity;

  private final EntityDeletionOrUpdateAdapter<PendingUploadEntity> __updateAdapterOfPendingUploadEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public PendingUploadDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPendingUploadEntity = new EntityInsertionAdapter<PendingUploadEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pending_uploads` (`id`,`telecallerId`,`toNumber`,`callStartTime`,`callEndTime`,`durationSeconds`,`recordingPath`,`recordingStatus`,`attemptCount`,`lastAttemptTime`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PendingUploadEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTelecallerId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTelecallerId());
        }
        if (entity.getToNumber() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getToNumber());
        }
        statement.bindLong(4, entity.getCallStartTime());
        statement.bindLong(5, entity.getCallEndTime());
        statement.bindLong(6, entity.getDurationSeconds());
        if (entity.getRecordingPath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRecordingPath());
        }
        if (entity.getRecordingStatus() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRecordingStatus());
        }
        statement.bindLong(9, entity.getAttemptCount());
        statement.bindLong(10, entity.getLastAttemptTime());
        statement.bindLong(11, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfPendingUploadEntity = new EntityDeletionOrUpdateAdapter<PendingUploadEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pending_uploads` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PendingUploadEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPendingUploadEntity = new EntityDeletionOrUpdateAdapter<PendingUploadEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pending_uploads` SET `id` = ?,`telecallerId` = ?,`toNumber` = ?,`callStartTime` = ?,`callEndTime` = ?,`durationSeconds` = ?,`recordingPath` = ?,`recordingStatus` = ?,`attemptCount` = ?,`lastAttemptTime` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PendingUploadEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTelecallerId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTelecallerId());
        }
        if (entity.getToNumber() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getToNumber());
        }
        statement.bindLong(4, entity.getCallStartTime());
        statement.bindLong(5, entity.getCallEndTime());
        statement.bindLong(6, entity.getDurationSeconds());
        if (entity.getRecordingPath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRecordingPath());
        }
        if (entity.getRecordingStatus() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRecordingStatus());
        }
        statement.bindLong(9, entity.getAttemptCount());
        statement.bindLong(10, entity.getLastAttemptTime());
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pending_uploads WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PendingUploadEntity entity,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPendingUploadEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PendingUploadEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPendingUploadEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PendingUploadEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPendingUploadEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
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
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllPendingUploads(
      final Continuation<? super List<PendingUploadEntity>> $completion) {
    final String _sql = "SELECT * FROM pending_uploads ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PendingUploadEntity>>() {
      @Override
      @NonNull
      public List<PendingUploadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTelecallerId = CursorUtil.getColumnIndexOrThrow(_cursor, "telecallerId");
          final int _cursorIndexOfToNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "toNumber");
          final int _cursorIndexOfCallStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "callStartTime");
          final int _cursorIndexOfCallEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "callEndTime");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfRecordingPath = CursorUtil.getColumnIndexOrThrow(_cursor, "recordingPath");
          final int _cursorIndexOfRecordingStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordingStatus");
          final int _cursorIndexOfAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptCount");
          final int _cursorIndexOfLastAttemptTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAttemptTime");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<PendingUploadEntity> _result = new ArrayList<PendingUploadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PendingUploadEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTelecallerId;
            if (_cursor.isNull(_cursorIndexOfTelecallerId)) {
              _tmpTelecallerId = null;
            } else {
              _tmpTelecallerId = _cursor.getString(_cursorIndexOfTelecallerId);
            }
            final String _tmpToNumber;
            if (_cursor.isNull(_cursorIndexOfToNumber)) {
              _tmpToNumber = null;
            } else {
              _tmpToNumber = _cursor.getString(_cursorIndexOfToNumber);
            }
            final long _tmpCallStartTime;
            _tmpCallStartTime = _cursor.getLong(_cursorIndexOfCallStartTime);
            final long _tmpCallEndTime;
            _tmpCallEndTime = _cursor.getLong(_cursorIndexOfCallEndTime);
            final int _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getInt(_cursorIndexOfDurationSeconds);
            final String _tmpRecordingPath;
            if (_cursor.isNull(_cursorIndexOfRecordingPath)) {
              _tmpRecordingPath = null;
            } else {
              _tmpRecordingPath = _cursor.getString(_cursorIndexOfRecordingPath);
            }
            final String _tmpRecordingStatus;
            if (_cursor.isNull(_cursorIndexOfRecordingStatus)) {
              _tmpRecordingStatus = null;
            } else {
              _tmpRecordingStatus = _cursor.getString(_cursorIndexOfRecordingStatus);
            }
            final int _tmpAttemptCount;
            _tmpAttemptCount = _cursor.getInt(_cursorIndexOfAttemptCount);
            final long _tmpLastAttemptTime;
            _tmpLastAttemptTime = _cursor.getLong(_cursorIndexOfLastAttemptTime);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new PendingUploadEntity(_tmpId,_tmpTelecallerId,_tmpToNumber,_tmpCallStartTime,_tmpCallEndTime,_tmpDurationSeconds,_tmpRecordingPath,_tmpRecordingStatus,_tmpAttemptCount,_tmpLastAttemptTime,_tmpCreatedAt);
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
  public Object getActivePendingUploads(
      final Continuation<? super List<PendingUploadEntity>> $completion) {
    final String _sql = "SELECT * FROM pending_uploads WHERE attemptCount < 48 ORDER BY createdAt ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PendingUploadEntity>>() {
      @Override
      @NonNull
      public List<PendingUploadEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTelecallerId = CursorUtil.getColumnIndexOrThrow(_cursor, "telecallerId");
          final int _cursorIndexOfToNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "toNumber");
          final int _cursorIndexOfCallStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "callStartTime");
          final int _cursorIndexOfCallEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "callEndTime");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfRecordingPath = CursorUtil.getColumnIndexOrThrow(_cursor, "recordingPath");
          final int _cursorIndexOfRecordingStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordingStatus");
          final int _cursorIndexOfAttemptCount = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptCount");
          final int _cursorIndexOfLastAttemptTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastAttemptTime");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<PendingUploadEntity> _result = new ArrayList<PendingUploadEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PendingUploadEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTelecallerId;
            if (_cursor.isNull(_cursorIndexOfTelecallerId)) {
              _tmpTelecallerId = null;
            } else {
              _tmpTelecallerId = _cursor.getString(_cursorIndexOfTelecallerId);
            }
            final String _tmpToNumber;
            if (_cursor.isNull(_cursorIndexOfToNumber)) {
              _tmpToNumber = null;
            } else {
              _tmpToNumber = _cursor.getString(_cursorIndexOfToNumber);
            }
            final long _tmpCallStartTime;
            _tmpCallStartTime = _cursor.getLong(_cursorIndexOfCallStartTime);
            final long _tmpCallEndTime;
            _tmpCallEndTime = _cursor.getLong(_cursorIndexOfCallEndTime);
            final int _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getInt(_cursorIndexOfDurationSeconds);
            final String _tmpRecordingPath;
            if (_cursor.isNull(_cursorIndexOfRecordingPath)) {
              _tmpRecordingPath = null;
            } else {
              _tmpRecordingPath = _cursor.getString(_cursorIndexOfRecordingPath);
            }
            final String _tmpRecordingStatus;
            if (_cursor.isNull(_cursorIndexOfRecordingStatus)) {
              _tmpRecordingStatus = null;
            } else {
              _tmpRecordingStatus = _cursor.getString(_cursorIndexOfRecordingStatus);
            }
            final int _tmpAttemptCount;
            _tmpAttemptCount = _cursor.getInt(_cursorIndexOfAttemptCount);
            final long _tmpLastAttemptTime;
            _tmpLastAttemptTime = _cursor.getLong(_cursorIndexOfLastAttemptTime);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new PendingUploadEntity(_tmpId,_tmpTelecallerId,_tmpToNumber,_tmpCallStartTime,_tmpCallEndTime,_tmpDurationSeconds,_tmpRecordingPath,_tmpRecordingStatus,_tmpAttemptCount,_tmpLastAttemptTime,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
