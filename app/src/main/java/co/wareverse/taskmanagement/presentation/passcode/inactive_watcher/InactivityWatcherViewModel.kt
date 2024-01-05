package co.wareverse.taskmanagement.presentation.passcode.inactive_watcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InactivityWatcherViewModel @Inject constructor(
    private val passcodeRepository: PasscodeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(InactivityWatcherState())
    val uiState = _uiState.asStateFlow()

    private var watcherJob: Job? = null

    fun watcher() {
        watcherJob?.cancel()
        watcherJob = viewModelScope.launch {
            while (!passcodeRepository.isInactive()) {
                delay(1_000)
            }

            _uiState.update { state ->
                state.copy(eventState = InactivityWatcherEventState.Inactive)
            }
        }
    }

    fun extendInactiveTime() {
        _uiState.update { state ->
            state.copy(
                inactiveMillis = passcodeRepository.extendInactiveTime()
            )
        }
    }
}