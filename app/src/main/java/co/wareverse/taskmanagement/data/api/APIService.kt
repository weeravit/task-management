package co.wareverse.taskmanagement.data.api

import co.wareverse.taskmanagement.data.model.TodoListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/todo-list")
    suspend fun fetchTodoList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20,
        @Query("sortBy") sortBy: String = "createdAt",
        @Query("isAsc") isAsc: Boolean = true,
        @Query("status") status: String,
    ): Response<TodoListDto>
}