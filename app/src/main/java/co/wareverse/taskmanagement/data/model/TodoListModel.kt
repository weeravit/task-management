package co.wareverse.taskmanagement.data.model

sealed class TodoListModel(
    open val id: String,
    open val date: String,
) {
    data class DateGroupTasksModel(
        override val date: String,
    ) : TodoListModel(
        id = date,
        date = date,
    )

    data class TaskModel(
        override val id: String,
        override val date: String,
        val title: String,
        val description: String,
        val status: String,
        val imageUrl: String,
        val createdAt: String,
    ) : TodoListModel(
        id = id,
        date = date,
    )
}