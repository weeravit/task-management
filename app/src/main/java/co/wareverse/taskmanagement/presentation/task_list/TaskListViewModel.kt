package co.wareverse.taskmanagement.presentation.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.data.model.TodoListModel
import co.wareverse.taskmanagement.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState = _uiState.asStateFlow()

    val todoPaging by lazy {
        taskRepository.getTaskPaging(TaskStatus.TODO)
            .cachedIn(viewModelScope)
    }
    val doingPaging by lazy {
        taskRepository.getTaskPaging(TaskStatus.DOING)
            .cachedIn(viewModelScope)
    }
    val donePaging by lazy {
        taskRepository.getTaskPaging(TaskStatus.DONE)
            .cachedIn(viewModelScope)
    }

    fun deleteTask(task: TodoListModel.TaskModel) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)

            _uiState.update { state ->
                state.copy(
                    eventState = TaskListEventState.TaskDeleted(task)
                )
            }
        }
    }
}