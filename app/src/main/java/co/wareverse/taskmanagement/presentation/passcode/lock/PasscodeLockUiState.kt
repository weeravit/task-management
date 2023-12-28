package co.wareverse.taskmanagement.presentation.passcode.lock

import androidx.compose.runtime.Stable

@Stable
data class PasscodeLockUiState(
    val eventState: PasscodeLockEventState = PasscodeLockEventState.Idle,
)

sealed interface PasscodeLockEventState {
    data object Idle: PasscodeLockEventState
    data object Incorrect: PasscodeLockEventState
    data object Passed: PasscodeLockEventState
}
