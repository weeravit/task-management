package co.wareverse.taskmanagement.presentation.passcode.lock

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.wareverse.taskmanagement.R
import co.wareverse.taskmanagement.core.theme.BackgroundColor
import co.wareverse.taskmanagement.presentation.passcode.component.PasscodeContent
import co.wareverse.taskmanagement.presentation.passcode.component.rememberPasscodeState

@Composable
fun PasscodeLockScreen(
    viewModel: PasscodeLockViewModel = hiltViewModel(),
    onSetupClick: () -> Unit,
    onPassed: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val passcodeState = rememberPasscodeState(
        onSetupClick = onSetupClick,
    )

    LaunchedEffect(uiState.eventState) {
        uiState.eventState
            .takeIf { it is PasscodeLockEventState.Passed }
            ?.let { onPassed() }

        uiState.eventState
            .takeIf { it is PasscodeLockEventState.Incorrect }
            ?.let {
                passcodeState.passcode = ""
                passcodeState.errorMessage =
                    context.getString(R.string.passcode_lock_screen_incorrect_text)
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
            title = stringResource(R.string.passcode_lock_screen_title_text),
            subtitle = stringResource(R.string.passcode_lock_screen_subtitle_text),
        )
    }
}
