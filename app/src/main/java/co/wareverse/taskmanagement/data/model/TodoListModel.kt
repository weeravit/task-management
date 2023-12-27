package co.wareverse.taskmanagement.data.model

sealed class TodoListModel(open val id: String) {
    data class DateGroupTasksModel(
        val date: String,
    ): TodoListModel(id = date)

    data class TaskModel(
        override val id: String,
        val title: String,
        val description: String,
        val status: String,
        val date: String,
        val imageUrl: String,
        val createdAt: String,
    ): TodoListModel(id = id)
}