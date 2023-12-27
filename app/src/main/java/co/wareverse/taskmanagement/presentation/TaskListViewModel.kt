package co.wareverse.taskmanagement.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.data.model.TodoListModel
import co.wareverse.taskmanagement.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState = _uiState.asStateFlow()

    private val _paging = MutableStateFlow<PagingData<TodoListModel>>(PagingData.empty())
    val paging = _paging.cachedIn(viewModelScope)

    fun load(status: TaskStatus = TaskStatus.TODO) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(filter = status)
            }

            taskRepository.getTaskList(status)
                .onEach { pagingData ->
                    _paging.update { pagingData }
                }.launchIn(this)
        }
    }
}