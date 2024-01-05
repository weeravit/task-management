package co.wareverse.taskmanagement.presentation.passcode.never_setup_watcher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PasscodeNeverSetupWatcher(
    viewModel: PasscodeNeverSetupWatcherViewModel = hiltViewModel(),
    onNeverSetup: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.watcher()
    }

    LaunchedEffect(uiState.eventState) {
        uiState.eventState
            .takeIf { it is PasscodeNeverSetupWatcherEventState.NeverSetup }
            ?.let { onNeverSetup() }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        content()
    }
}
