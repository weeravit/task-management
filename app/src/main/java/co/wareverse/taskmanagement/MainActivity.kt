package co.wareverse.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.wareverse.taskmanagement.presentation.TaskListScreen
import co.wareverse.taskmanagement.core.theme.TaskManagementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                TaskListScreen()
            }
        }
    }
}
