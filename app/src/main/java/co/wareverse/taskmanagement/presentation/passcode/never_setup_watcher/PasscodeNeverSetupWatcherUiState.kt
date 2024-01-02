package co.wareverse.taskmanagement.presentation.passcode.never_setup_watcher

import androidx.compose.runtime.Stable

@Stable
data class PasscodeNeverSetupWatcherUiState(
    val eventState: PasscodeNeverSetupWatcherEventState = PasscodeNeverSetupWatcherEventState.Idle,
)

sealed interface PasscodeNeverSetupWatcherEventState {
    data object Idle: PasscodeNeverSetupWatcherEventState
    data object NeverSetup: PasscodeNeverSetupWatcherEventState
}
