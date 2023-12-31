package co.wareverse.taskmanagement.presentation.passcode.setup

import androidx.compose.runtime.Stable

@Stable
data class PasscodeSetupUiState(
    val eventState: PasscodeSetupEventState = PasscodeSetupEventState.Idle,
)

sealed interface PasscodeSetupEventState {
    data object Idle: PasscodeSetupEventState
    data object NeverSetup: PasscodeSetupEventState
    data class Setup(val passcode: String): PasscodeSetupEventState
    data object Mismatch: PasscodeSetupEventState
    data object Passed: PasscodeSetupEventState
}
