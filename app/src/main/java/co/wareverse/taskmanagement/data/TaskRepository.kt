package co.wareverse.taskmanagement.data

import co.wareverse.taskmanagement.core.APIService
import co.wareverse.taskmanagement.core.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val apiService: APIService,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getTodoList(
        status: TaskStatus,
        offset: Int = 0,
        limit: Int = 20,
    ): List<TodoListModel> = withContext(dispatcher) {
        val response = apiService.fetchTodoList(
            offset = offset,
            limit = limit,
            sortBy = "createdAt",
            isAsc = true,
            status = status.value,
        )

        return@withContext response.body()
            ?.tasks
            ?.groupBy { it.createdAt }
            ?.map {
                TodoListModel(
                    date = it.key.orEmpty(),
                    tasks = it.value.map { task ->
                        TodoListModel.Task(
                            id = task.id.orEmpty(),
                            createdAt = task.createdAt.orEmpty(),
                            description = task.description.orEmpty(),
                            status = task.status.orEmpty(),
                            title = task.title.orEmpty(),
                        )
                    }
                )
            }.orEmpty()
    }
}