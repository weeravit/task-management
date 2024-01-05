package co.wareverse.taskmanagement.data.model

enum class TaskStatus(
    val value: String,
    val display: String,
) {
    TODO("TODO", "To-do"),
    DOING("DOING", "Doing"),
    DONE("DONE", "Done"),
}