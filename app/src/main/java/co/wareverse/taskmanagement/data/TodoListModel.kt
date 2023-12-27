package co.wareverse.taskmanagement.data

data class TodoListModel(
    val date: String,
    val tasks: List<Task>,
) {
    data class Task(
        val id: String,
        val createdAt: String,
        val description: String,
        val status: String,
        val title: String,
    )
}