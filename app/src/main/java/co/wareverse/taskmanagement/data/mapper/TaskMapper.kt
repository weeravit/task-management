package co.wareverse.taskmanagement.data.mapper

import co.wareverse.taskmanagement.core.extension.API_PATTERN
import co.wareverse.taskmanagement.core.extension.d_MMMM_yyyy
import co.wareverse.taskmanagement.core.extension.toLocalDateTime
import co.wareverse.taskmanagement.core.extension.toPattern
import co.wareverse.taskmanagement.data.model.TaskEntity
import co.wareverse.taskmanagement.data.model.TodoListModel

fun TaskEntity.toModel(): TodoListModel.TaskModel {
    return TodoListModel.TaskModel(
        id = this.id,
        title = this.title.orEmpty(),
        description = this.description.orEmpty(),
        status = this.status.orEmpty(),
        createdAt = this.createdAt.orEmpty(),
        imageUrl = "https://picsum.photos/100?random=${(0..50).random()}",
        date = this.createdAt.orEmpty()
            .toLocalDateTime(API_PATTERN)
            .toPattern(d_MMMM_yyyy)
            .uppercase(),
    )
}