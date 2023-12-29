package co.wareverse.taskmanagement.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity("task")
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo("id")
    @SerialName("id")
    val id: String,

    @ColumnInfo("createdAt")
    @SerialName("createdAt")
    val createdAt: String? = null,

    @ColumnInfo("description")
    @SerialName("description")
    val description: String? = null,

    @ColumnInfo("status")
    @SerialName("status")
    val status: String? = null,

    @ColumnInfo("title")
    @SerialName("title")
    val title: String? = null
)
