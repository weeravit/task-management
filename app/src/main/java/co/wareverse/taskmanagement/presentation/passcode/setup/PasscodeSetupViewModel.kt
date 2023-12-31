package co.wareverse.taskmanagement.presentation.passcode.setup

import androidx.lifecycle.ViewModel
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PasscodeSetupViewModel @Inject constructor(
    private val passcodeRepository: PasscodeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PasscodeSetupUiState())
    val uiState = _uiState.asStateFlow()

    fun neverSetupWatcher() {
        _uiState.update { state ->
            val eventState = when {
                passcodeRepository.isSetup() -> PasscodeSetupEventState.Idle
                else -> PasscodeSetupEventState.NeverSetup
            }

            state.copy(
                eventState = eventState,
            )
        }
    }

    fun setupNewPassword(passcode: String) {
        _uiState.update { state ->
            state.copy(
                eventState = PasscodeSetupEventState.Setup(passcode),
            )
        }
    }

    fun confirmSetup(
        passcode: String,
        confirmPasscode: String,
    ) {
        _uiState.update { state ->
            try {
                passcodeRepository.setup(
                    passcode = passcode,
                    confirmPasscode = confirmPasscode,
                )
                passcodeRepository.extendInactiveTime()

                state.copy(
                    eventState = PasscodeSetupEventState.Passed,
                )
            } catch (e: Exception) {
                state.copy(
                    eventState = PasscodeSetupEventState.Mismatch,
                )
            }
        }
    }

    fun clearEventState() {
        _uiState.update { state ->
            state.copy(eventState = PasscodeSetupEventState.Idle)
        }
    }
}