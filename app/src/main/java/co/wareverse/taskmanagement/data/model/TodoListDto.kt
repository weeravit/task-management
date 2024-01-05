package co.wareverse.taskmanagement.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoListDto(
    @SerialName("pageNumber")
    val pageNumber: Int? = null,
    @SerialName("tasks")
    val tasks: List<TaskEntity>? = null,
    @SerialName("totalPages")
    val totalPages: Int? = null
)