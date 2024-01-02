package co.wareverse.taskmanagement.presentation.passcode.inactive_watcher

import co.wareverse.taskmanagement.MainDispatcherRule
import co.wareverse.taskmanagement.core.extension.nowInMillis
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import co.wareverse.taskmanagement.presentation.passcode.inactive_watcher.InactivityWatcherEventState
import co.wareverse.taskmanagement.presentation.passcode.inactive_watcher.InactivityWatcherViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class InactivityWatcherViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var passcodeRepository: PasscodeRepository
    private lateinit var viewModel: InactivityWatcherViewModel

    @Before
    fun setup() {
        passcodeRepository = mock()
        viewModel = InactivityWatcherViewModel(
            passcodeRepository = passcodeRepository,
        )
    }

    @Test
    fun `should show inactive when expire time is in past`() = runTest {
        // Given
        whenever(passcodeRepository.isInactive()).thenReturn(true)

        // When
        viewModel.watcher()

        // Then
        assertEquals(InactivityWatcherEventState.Inactive, viewModel.uiState.value.eventState)
    }

//    @Test
//    fun `should active when expire time is in future`() = runTest {
//        // Given
//        whenever(passcodeRepository.isInactive()).thenReturn(false)
//
//        // When
//        viewModel.watcher()
//
//        // Then
//        assertEquals(InactivityWatcherEventState.Active, viewModel.uiState.value.eventState)
//    }

    @Test
    fun `should got new expire time when extend time invoked`() = runTest {
        // Given
        val newInactiveMillis = nowInMillis().plus(10_000)
        whenever(passcodeRepository.extendInactiveTime()).thenReturn(newInactiveMillis)

        // When
        viewModel.extendInactiveTime()

        // Then
        assertNotSame(0, viewModel.uiState.value.inactiveMillis)
    }
}