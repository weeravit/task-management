package co.wareverse.taskmanagement.presentation.passcode.inactive_watcher

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun InactivityWatcher(
    viewModel: InactivityWatcherViewModel = hiltViewModel(),
    onInactive: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.watcher()
    }

    LaunchedEffect(uiState.eventState) {
        uiState.eventState
            .takeIf { it is InactivityWatcherEventState.Inactive }
            ?.let { onInactive() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        viewModel.extendInactiveTime()
                    }
                )
            }
    ) {
        content()
    }
}
