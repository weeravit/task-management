package co.wareverse.taskmanagement.presentation.passcode.setup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import co.wareverse.taskmanagement.core.theme.TextColor
import co.wareverse.taskmanagement.presentation.passcode.component.PasscodeContent
import co.wareverse.taskmanagement.presentation.passcode.component.rememberPasscodeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasscodeSetupConfirmScreen(
    viewModel: PasscodeSetupViewModel = hiltViewModel(),
    passcode: String,
    onBackClick: () -> Unit,
    onPassed: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val passcodeState = rememberPasscodeState()

    LaunchedEffect(uiState.eventState) {
        uiState.eventState
            .takeIf { it is PasscodeSetupEventState.Passed }
            ?.let {
                onPassed()
                viewModel.clearEventState()
            }

        uiState.eventState
            .takeIf { it is PasscodeSetupEventState.Mismatch }
            ?.let {
                passcodeState.passcode = ""
                passcodeState.errorMessage =
                    context.getString(R.string.confirm_setup_passcode_screen_mismatch_text)
                viewModel.clearEventState()
            }
    }

    LaunchedEffect(passcodeState.passcode) {
        passcodeState.passcode
            .takeIf { it.isNotEmpty() }
            ?.let { passcodeState.errorMessage = "" }

        passcodeState.passcode
            .takeIf { it.length == 6 }
            ?.let {
                viewModel.confirmSetup(
                    passcode = passcode,
                    confirmPasscode = it
                )
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        contentColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = TextColor,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        PasscodeContent(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            state = passcodeState,
            title = stringResource(R.string.confirm_setup_passcode_screen_title_text),
            subtitle = stringResource(R.string.confirm_setup_passcode_screen_subtitle_text),
        )
    }
}
