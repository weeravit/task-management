package co.wareverse.taskmanagement.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.wareverse.taskmanagement.data.model.RemoteKeyEntity
import co.wareverse.taskmanagement.data.model.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        RemoteKeyEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}