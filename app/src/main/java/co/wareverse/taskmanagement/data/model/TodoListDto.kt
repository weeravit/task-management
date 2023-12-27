package co.wareverse.taskmanagement.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoListDto(
    @SerialName("pageNumber")
    val pageNumber: Int? = null,
    @SerialName("tasks")
    val tasks: List<Task>? = null,
    @SerialName("totalPages")
    val totalPages: Int? = null
) {
    @Serializable
    data class Task(
        @SerialName("createdAt")
        val createdAt: String? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("id")
        val id: String? = null,
        @SerialName("status")
        val status: String? = null,
        @SerialName("title")
        val title: String? = null
    )
}