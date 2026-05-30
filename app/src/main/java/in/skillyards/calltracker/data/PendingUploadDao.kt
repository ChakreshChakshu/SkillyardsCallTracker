package `in`.skillyards.calltracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PendingUploadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PendingUploadEntity): Long

    @Update
    suspend fun update(entity: PendingUploadEntity)

    @Delete
    suspend fun delete(entity: PendingUploadEntity)

    @Query("SELECT * FROM pending_uploads ORDER BY createdAt ASC")
    suspend fun getAllPendingUploads(): List<PendingUploadEntity>

    @Query("SELECT * FROM pending_uploads WHERE attemptCount < 48 ORDER BY createdAt ASC")
    suspend fun getActivePendingUploads(): List<PendingUploadEntity>

    @Query("DELETE FROM pending_uploads WHERE id = :id")
    suspend fun deleteById(id: Int)
}
