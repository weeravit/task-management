package co.wareverse.taskmanagement.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.wareverse.taskmanagement.core.extension.enterSlideUp
import co.wareverse.taskmanagement.core.extension.exitSlideUp
import co.wareverse.taskmanagement.core.extension.popEnterSlideDown
import co.wareverse.taskmanagement.core.extension.popExitSlideDown
import co.wareverse.taskmanagement.core.theme.TaskManagementTheme
import co.wareverse.taskmanagement.presentation.passcode.inactive_watcher.InactivityWatcher
import co.wareverse.taskmanagement.presentation.passcode.lock.PasscodeLockScreen
import co.wareverse.taskmanagement.presentation.task_list.TaskListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskManagementTheme {
                val mainNavController = rememberNavController()

                NavHost(
                    navController = mainNavController,
                    startDestination = NavRoute.TASK_LIST_ROUTE,
                ) {
                    composable(NavRoute.TASK_LIST_ROUTE) {
                        InactivityWatcher(
                            onInactive = {
                                mainNavController.navigate(route = NavRoute.PASSCODE_LOCK_ROUTE) {
                                    popUpTo(NavRoute.PASSCODE_LOCK_ROUTE) { inclusive = true }
                                }
                            }
                        ) {
                            TaskListScreen()
                        }
                    }

                    composable(
                        route = NavRoute.PASSCODE_LOCK_ROUTE,
                        enterTransition = { enterSlideUp() },
                        exitTransition = { exitSlideUp() },
                        popEnterTransition = { popEnterSlideDown() },
                        popExitTransition = { popExitSlideDown() }
                    ) {
                        PasscodeLockScreen(
                            onPassed = {
                                mainNavController.navigate(route = NavRoute.TASK_LIST_ROUTE) {
                                    popUpTo(NavRoute.TASK_LIST_ROUTE) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
