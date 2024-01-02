package co.wareverse.taskmanagement.presentation.passcode.lock

import co.wareverse.taskmanagement.MainDispatcherRule
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import co.wareverse.taskmanagement.presentation.passcode.lock.PasscodeLockEventState
import co.wareverse.taskmanagement.presentation.passcode.lock.PasscodeLockViewModel
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PasscodeLockViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var passcodeRepository: PasscodeRepository
    private lateinit var viewModel: PasscodeLockViewModel

    @Before
    fun setup() {
        passcodeRepository = mock()
        viewModel = PasscodeLockViewModel(
            passcodeRepository = passcodeRepository,
        )
    }

    @Test
    fun `should show passed when passcode correctly`() = runTest {
        // Given
        val passcode = "123456"
        whenever(passcodeRepository.isPasscodeValid(passcode)).thenReturn(true)

        // When
        viewModel.submit(passcode)

        // Then
        TestCase.assertEquals(PasscodeLockEventState.Passed, viewModel.uiState.value.eventState)
    }

    @Test
    fun `should show invalid when passcode incorrect`() = runTest {
        // Given
        val passcode = "111111"
        whenever(passcodeRepository.isPasscodeValid(passcode)).thenReturn(false)

        // When
        viewModel.submit(passcode)

        // Then
        TestCase.assertEquals(PasscodeLockEventState.Incorrect, viewModel.uiState.value.eventState)
    }

    @Test
    fun `should show idle when clear event state`() = runTest {
        // Given
        val passcode = "111111"
        whenever(passcodeRepository.isPasscodeValid(passcode)).thenReturn(false)

        // When
        viewModel.submit(passcode)
        viewModel.clearEventState()

        // Then
        TestCase.assertEquals(PasscodeLockEventState.Idle, viewModel.uiState.value.eventState)
    }
}