package co.wareverse.taskmanagement.presentation.passcode.lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasscodeLockViewModel @Inject constructor(
    private val passcodeRepository: PasscodeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PasscodeLockUiState())
    val uiState = _uiState.asStateFlow()

    fun submit(passcode: String) {
        viewModelScope.launch {
            if (passcodeRepository.isPasscodeValid(passcode)) {
                passcodeRepository.extendInactiveTime()

                _uiState.update { state ->
                    state.copy(eventState = PasscodeLockEventState.Passed)
                }
            } else {
                _uiState.update { state ->
                    state.copy(eventState = PasscodeLockEventState.Incorrect)
                }
            }
        }
    }

    fun clearEventState() {
        _uiState.update { state ->
            state.copy(eventState = PasscodeLockEventState.Idle)
        }
    }
}