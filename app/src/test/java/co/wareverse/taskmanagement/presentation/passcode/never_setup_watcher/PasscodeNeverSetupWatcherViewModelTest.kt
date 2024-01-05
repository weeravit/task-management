package co.wareverse.taskmanagement.presentation.passcode.never_setup_watcher

import co.wareverse.taskmanagement.MainDispatcherRule
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import co.wareverse.taskmanagement.presentation.passcode.never_setup_watcher.PasscodeNeverSetupWatcherEventState
import co.wareverse.taskmanagement.presentation.passcode.never_setup_watcher.PasscodeNeverSetupWatcherViewModel
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PasscodeNeverSetupWatcherViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var passcodeRepository: PasscodeRepository
    private lateinit var viewModel: PasscodeNeverSetupWatcherViewModel

    @Before
    fun setup() {
        passcodeRepository = mock()
        viewModel = PasscodeNeverSetupWatcherViewModel(
            passcodeRepository = passcodeRepository,
        )
    }

    @Test
    fun `should show never setup when passcode is never setup`() = runTest {
        // Given
        whenever(passcodeRepository.isSetup()).thenReturn(false)

        // When
        viewModel.watcher()

        // Then
        TestCase.assertEquals(PasscodeNeverSetupWatcherEventState.NeverSetup, viewModel.uiState.value.eventState)
    }

    @Test
    fun `should show idle when passcode is setup`() = runTest {
        // Given
        whenever(passcodeRepository.isSetup()).thenReturn(true)

        // When
        viewModel.watcher()

        // Then
        TestCase.assertEquals(PasscodeNeverSetupWatcherEventState.Idle, viewModel.uiState.value.eventState)
    }
}