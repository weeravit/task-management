package co.wareverse.taskmanagement.presentation.passcode.never_setup_watcher

import androidx.lifecycle.ViewModel
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PasscodeNeverSetupWatcherViewModel @Inject constructor(
    private val passcodeRepository: PasscodeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PasscodeNeverSetupWatcherUiState())
    val uiState = _uiState.asStateFlow()

    fun watcher() {
        _uiState.update { state ->
            val eventState = when {
                passcodeRepository.isSetup() -> PasscodeNeverSetupWatcherEventState.Idle
                else -> PasscodeNeverSetupWatcherEventState.NeverSetup
            }

            state.copy(
                eventState = eventState,
            )
        }
    }
}