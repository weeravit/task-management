package co.wareverse.taskmanagement.data.repository

import androidx.paging.ExperimentalPagingApi
import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.data.api.APIService
import co.wareverse.taskmanagement.data.local.AppDatabase
import co.wareverse.taskmanagement.data.local.TaskDao
import co.wareverse.taskmanagement.data.model.TodoListModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class TaskRepositoryTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var apiService: APIService
    private lateinit var appDatabase: AppDatabase
    private lateinit var appConfig: AppConfig
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        apiService = mock()
        appDatabase = mock()
        appConfig = mock()
        taskRepository = TaskRepository(
            apiService = apiService,
            appDatabase = appDatabase,
            appConfig = appConfig,
            dispatcher = dispatcher,
        )
    }

    @Test
    fun `should delete task when dao delete invoked`() = runTest {
        // Given
        val task = TodoListModel.TaskModel(
            id = "1",
            date = "",
            title = "",
            description = "",
            status = "",
            imageUrl = "",
            createdAt = "",
        )
        val taskDaoMock = mock<TaskDao>()
        whenever(appDatabase.taskDao()).thenReturn(taskDaoMock)

        // When
        taskRepository.deleteTask(task)

        // Then
        verify(taskDaoMock).delete(task.id)
    }
}