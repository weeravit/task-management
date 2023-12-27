package co.wareverse.taskmanagement.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import co.wareverse.taskmanagement.core.extension.d_MMMM_yyyy
import co.wareverse.taskmanagement.core.extension.API_PATTERN
import co.wareverse.taskmanagement.core.extension.toLocalDateTime
import co.wareverse.taskmanagement.core.extension.toPattern
import co.wareverse.taskmanagement.data.api.APIService
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.data.model.TodoListModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val apiService: APIService,
) {
    fun getTaskList(
        status: TaskStatus,
        limit: Int = 20,
    ): Flow<PagingData<TodoListModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                initialLoadSize = limit,
            ),
            pagingSourceFactory = {
                TodoListPagingSource(
                    apiService = apiService,
                    taskStatus = status,
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { dto ->
                TodoListModel.TaskModel(
                    id = dto.id.orEmpty(),
                    title = dto.title.orEmpty(),
                    description = dto.description.orEmpty(),
                    status = dto.status.orEmpty(),
                    createdAt = dto.createdAt.orEmpty(),
                    imageUrl = "https://picsum.photos/100?random=${(0..50).random()}",
                    date = dto.createdAt.orEmpty()
                        .toLocalDateTime(API_PATTERN)
                        .toPattern(d_MMMM_yyyy)
                        .uppercase(),
                )
            }.insertSeparators { before: TodoListModel.TaskModel?, after: TodoListModel.TaskModel? ->
                    if (after != null && (before == null || before.date != after.date)) {
                        TodoListModel.DateGroupTasksModel(
                            date = after.date
                        )
                    } else {
                        null
                    }
                }
        }
    }
}