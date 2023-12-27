package co.wareverse.taskmanagement.presentation

import androidx.compose.runtime.Stable
import co.wareverse.taskmanagement.data.model.TaskStatus

@Stable
data class TaskListUiState(
    val filter: TaskStatus = TaskStatus.TODO
)
