package co.wareverse.taskmanagement.presentation.passcode.inactive_watcher

import androidx.compose.runtime.Stable

@Stable
data class InactivityWatcherState(
    val inactiveMillis: Long = 0,
    val eventState: InactivityWatcherEventState = InactivityWatcherEventState.Active,
)

sealed interface InactivityWatcherEventState {
    data object Active: InactivityWatcherEventState
    data object Inactive: InactivityWatcherEventState
}
