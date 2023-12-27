package co.wareverse.taskmanagement.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.wareverse.taskmanagement.data.api.APIService
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.data.model.TodoListDto

class TodoListPagingSource(
    private val apiService: APIService,
    private val taskStatus: TaskStatus,
) : PagingSource<Int, TodoListDto.Task>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TodoListDto.Task> {
        return try {
            val page = params.key ?: 0
            val limit = params.loadSize

            val response = apiService.fetchTodoList(
                offset = page,
                limit = limit,
                sortBy = "createdAt",
                isAsc = true,
                status = taskStatus.value,
            )
            val dto = response.body()

            val totalPage = dto?.totalPages?.minus(1) ?: 0
            val tasks = dto?.tasks.orEmpty()

            val nextKey = if (page < totalPage) {
                page.plus(1)
            } else {
                null
            }

            LoadResult.Page(
                prevKey = null,
                nextKey = nextKey,
                data = tasks,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TodoListDto.Task>): Int? {
        return null
    }
}