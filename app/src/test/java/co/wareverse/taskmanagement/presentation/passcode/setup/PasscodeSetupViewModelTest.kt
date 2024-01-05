package co.wareverse.taskmanagement.presentation.passcode.setup

import co.wareverse.taskmanagement.MainDispatcherRule
import co.wareverse.taskmanagement.data.exception.PasscodeMismatchException
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import co.wareverse.taskmanagement.presentation.passcode.setup.PasscodeSetupEventState
import co.wareverse.taskmanagement.presentation.passcode.setup.PasscodeSetupViewModel
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PasscodeSetupViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var passcodeRepository: PasscodeRepository
    private lateinit var viewModel: PasscodeSetupViewModel

    @Before
    fun setup() {
        passcodeRepository = mock()
        viewModel = PasscodeSetupViewModel(
            passcodeRepository = passcodeRepository,
        )
    }

    @Test
    fun `should show setup confirm when setup a new passcode`() = runTest {
        // Given
        val passcode = "112233"

        // When
        viewModel.setupNewPassword(passcode)

        // Then
        TestCase.assertEquals(PasscodeSetupEventState.Setup(passcode), viewModel.uiState.value.eventState)
    }

    @Test
    fun `should show match passcode when input confirm passcode correctly`() = runTest {
        // Given
        val passcode = "112233"
        val confirmPasscode = "112233"
        whenever(passcodeRepository.setup(passcode, confirmPasscode))
            .then { }

        // When
        viewModel.confirmSetup(passcode, confirmPasscode)

        // Then
        TestCase.assertEquals(PasscodeSetupEventState.Passed, viewModel.uiState.value.eventState)
    }

    @Test
    fun `should show mismatch passcode when input confirm passcode incorrect`() = runTest {
        // Given
        val passcode = "112233"
        val confirmPasscode = "111111"
        whenever(passcodeRepository.setup(passcode, confirmPasscode))
            .then { throw PasscodeMismatchException() }

        // When
        viewModel.confirmSetup(passcode, confirmPasscode)

        // Then
        TestCase.assertEquals(PasscodeSetupEventState.Mismatch, viewModel.uiState.value.eventState)
    }

    @Test
    fun `should show idle when clear event state`() = runTest {
        // Given
        val passcode = "112233"
        val confirmPasscode = "111111"
        whenever(passcodeRepository.setup(passcode, confirmPasscode))
            .then { throw PasscodeMismatchException() }

        // When
        viewModel.confirmSetup(passcode, confirmPasscode)
        viewModel.clearEventState()

        // Then
        TestCase.assertEquals(PasscodeSetupEventState.Idle, viewModel.uiState.value.eventState)
    }
}