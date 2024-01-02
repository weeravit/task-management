package co.wareverse.taskmanagement.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import co.wareverse.taskmanagement.core.di.IODispatcher
import co.wareverse.taskmanagement.data.api.APIService
import co.wareverse.taskmanagement.data.local.AppDatabase
import co.wareverse.taskmanagement.data.mapper.toModel
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.data.model.TodoListModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TaskRepository @Inject constructor(
    private val apiService: APIService,
    private val appDatabase: AppDatabase,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {
    fun getTaskPaging(
        status: TaskStatus,
        limit: Int = 20,
    ): Flow<PagingData<TodoListModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = limit,
                initialLoadSize = limit,
            ),
            remoteMediator = TaskRemoteMediator(
                taskStatus = status,
                apiService = apiService,
                appDatabase = appDatabase,
            ),
            pagingSourceFactory = { appDatabase.taskDao().pagingSource(status.value) },
        ).flow.map { pagingData ->
            pagingData.map { it.toModel() }
                .insertSeparators { before: TodoListModel.TaskModel?,
                                    after: TodoListModel.TaskModel? ->
                    val isHeader = after != null && (before == null || before.date != after.date)

                    takeIf { isHeader }
                        ?.let {
                            TodoListModel.DateGroupTasksModel(
                                date = after?.date.orEmpty(),
                            )
                        }
                }
        }
    }

    suspend fun deleteTask(task: TodoListModel.TaskModel) = withContext(dispatcher) {
        appDatabase.taskDao().delete(task.id)
    }
}