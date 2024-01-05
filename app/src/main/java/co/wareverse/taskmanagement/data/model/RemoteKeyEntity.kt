package co.wareverse.taskmanagement.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo("label")
    val label: String,

    @ColumnInfo("nextKey")
    val nextKey: Int,
)