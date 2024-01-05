package co.wareverse.taskmanagement.presentation.task_list

import androidx.compose.runtime.Stable
import co.wareverse.taskmanagement.data.model.TodoListModel

@Stable
data class TaskListUiState(
    val eventState: TaskListEventState = TaskListEventState.Idle,
)

sealed interface TaskListEventState {
    data object Idle: TaskListEventState
    data class TaskDeleted(val task: TodoListModel.TaskModel): TaskListEventState
}
