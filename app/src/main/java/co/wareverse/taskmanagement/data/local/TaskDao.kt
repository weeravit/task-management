package co.wareverse.taskmanagement.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.wareverse.taskmanagement.data.model.TaskEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TaskEntity>)

    @Query("SELECT * FROM task WHERE status = :status")
    fun pagingSource(status: String): PagingSource<Int, TaskEntity>

    @Query("DELETE FROM task WHERE status = :query")
    suspend fun deleteByStatus(query: String)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun delete(id: String)
}