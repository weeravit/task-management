package co.wareverse.taskmanagement.presentation.passcode.setup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Composable
fun PasscodeSetupScreen(
    viewModel: PasscodeSetupViewModel = hiltViewModel(),
    onPassed: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val passcodeState = rememberPasscodeState()

    LaunchedEffect(uiState.eventState) {
        uiState.eventState
            .takeIf { it is PasscodeSetupEventState.Setup }
            ?.let { it as PasscodeSetupEventState.Setup }
            ?.let {
                onPassed(it.passcode)
                viewModel.clearEventState()
            }
    }

    LaunchedEffect(passcodeState.passcode) {
        passcodeState.passcode
            .takeIf { it.isNotEmpty() }
            ?.let { passcodeState.errorMessage = "" }

        passcodeState.passcode
            .takeIf { it.length == 6 }
            ?.let { viewModel.setupNewPassword(it) }
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
            title = "SETUP A NEW PASSCODE",
            subtitle = "Please enter your passcode",
        )
    }
}
