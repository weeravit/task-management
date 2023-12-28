package co.wareverse.taskmanagement.presentation.passcode.lock

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.wareverse.taskmanagement.core.theme.BackgroundColor
import co.wareverse.taskmanagement.presentation.passcode.component.PasscodeContent
import co.wareverse.taskmanagement.presentation.passcode.component.rememberPasscodeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasscodeLockScreen(
    viewModel: PasscodeLockViewModel = hiltViewModel(),
    onPassed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val passcodeState = rememberPasscodeState()

    LaunchedEffect(uiState.eventState) {
        uiState.eventState
            .takeIf { it is PasscodeLockEventState.Passed }
            ?.let { onPassed() }

        uiState.eventState
            .takeIf { it is PasscodeLockEventState.Incorrect }
            ?.let {
                passcodeState.passcode = ""
                passcodeState.errorMessage = "Incorrect Passcode. Please try again."
                viewModel.clearEventState()
            }
    }

    LaunchedEffect(passcodeState.passcode) {
        passcodeState.passcode
            .takeIf { it.isNotEmpty() }
            ?.let { passcodeState.errorMessage = "" }

        passcodeState.passcode
            .takeIf { it.length == 6 }
            ?.let { viewModel.submit(it) }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        contentColor = Color.Black,
    ) {
        PasscodeContent(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            state = passcodeState,
            title = "ENTER PASSCODE",
            subtitle = "Please enter your passcode",
        )
    }
}
