package co.wareverse.taskmanagement.presentation.task_list

import co.wareverse.taskmanagement.MainDispatcherRule
import co.wareverse.taskmanagement.data.model.TaskEntity
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.data.model.TodoListModel
import co.wareverse.taskmanagement.data.repository.TaskRepository
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import java.util.UUID

class TaskListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var taskRepository: TaskRepository
    private lateinit var viewModel: TaskListViewModel

    @Before
    fun setup() {
        taskRepository = mock()
        viewModel = TaskListViewModel(
            taskRepository = taskRepository,
        )
    }

//    @Test
//    fun `should display 10 items when load with todo status`() = runTest {
//        // Given
//        val status = TaskStatus.TODO
//        val todoList = createTaskEntityList(
//            size = 10,
//            status = TaskStatus.TODO,
//        ).map { it.toModel() }
//        whenever(taskRepository.getTaskPaging(status))
//            .thenReturn(flowOf(PagingData.from(todoList)))
//
//        // When
//        val items = viewModel.paging.asSnapshot {
//            scrollTo(10)
//        }
//        viewModel.load(status)
//
//        // Then
//        TestCase.assertEquals(todoList.size, items.size)
//    }

    @Test
    fun `should show task deleted when swipe task`() = runTest {
        // Given
        val task = mock<TodoListModel.TaskModel>()

        // When
        viewModel.deleteTask(task)

        // Then
        TestCase.assertEquals(TaskListEventState.TaskDeleted(task), viewModel.uiState.value.eventState)
    }

    private fun createTaskEntityList(size: Int, status: TaskStatus): List<TaskEntity> {
        return (1..size).map {
            val uuid = UUID.randomUUID().toString()

            TaskEntity(
                id = uuid,
                createdAt = "2024-01-01T09:09:09Z",
                title = uuid,
                description = uuid,
                status = status.value,
            )
        }
    }
}