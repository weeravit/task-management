package co.wareverse.taskmanagement.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.wareverse.taskmanagement.core.extension.enterSlideLeft
import co.wareverse.taskmanagement.core.extension.enterSlideUp
import co.wareverse.taskmanagement.core.extension.exitSlideLeft
import co.wareverse.taskmanagement.core.extension.exitSlideUp
import co.wareverse.taskmanagement.core.extension.popEnterSlideDown
import co.wareverse.taskmanagement.core.extension.popEnterSlideRight
import co.wareverse.taskmanagement.core.extension.popExitSlideDown
import co.wareverse.taskmanagement.core.extension.popExitSlideRight
import co.wareverse.taskmanagement.core.extension.popUpTo
import co.wareverse.taskmanagement.core.theme.TaskManagementTheme
import co.wareverse.taskmanagement.presentation.passcode.inactive_watcher.InactivityWatcher
import co.wareverse.taskmanagement.presentation.passcode.lock.PasscodeLockScreen
import co.wareverse.taskmanagement.presentation.passcode.never_setup_watcher.PasscodeNeverSetupWatcher
import co.wareverse.taskmanagement.presentation.passcode.setup.PasscodeSetupConfirmScreen
import co.wareverse.taskmanagement.presentation.passcode.setup.PasscodeSetupScreen
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
                        PasscodeNeverSetupWatcher(
                            onNeverSetup = {
                                mainNavController.popUpTo(NavRoute.PASSCODE_SETUP_ROUTE)
                            }
                        ) {
                            InactivityWatcher(
                                onInactive = {
                                    mainNavController.popUpTo(NavRoute.PASSCODE_LOCK_ROUTE)
                                }
                            ) {
                                TaskListScreen()
                            }
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
                            onSetupClick = { mainNavController.navigate(NavRoute.PASSCODE_SETUP_ROUTE) },
                            onPassed = { mainNavController.popUpTo(NavRoute.TASK_LIST_ROUTE) }
                        )
                    }

                    composable(
                        route = NavRoute.PASSCODE_SETUP_ROUTE,
                        enterTransition = { enterSlideLeft() },
                        exitTransition = { exitSlideLeft() },
                        popEnterTransition = { popEnterSlideRight() },
                        popExitTransition = { popExitSlideRight() },
                    ) {
                        PasscodeSetupScreen(
                            onPassed = {
                                mainNavController.navigate(
                                    route = "${NavRoute.PASSCODE_SETUP_CONFIRM_ROUTE}?${NavRoute.PASSCODE_ARG}=${it}"
                                )
                            }
                        )
                    }

                    composable(
                        route = "${NavRoute.PASSCODE_SETUP_CONFIRM_ROUTE}?${NavRoute.PASSCODE_ARG}={${NavRoute.PASSCODE_ARG}}",
                        arguments = listOf(
                            navArgument(NavRoute.PASSCODE_ARG) { defaultValue = "" },
                        ),
                        enterTransition = { enterSlideLeft() },
                        exitTransition = { exitSlideLeft() },
                        popEnterTransition = { popEnterSlideRight() },
                        popExitTransition = { popExitSlideRight() },
                    ) { backStackEntry ->
                        val passcode =
                            backStackEntry.arguments?.getString(NavRoute.PASSCODE_ARG).orEmpty()

                        PasscodeSetupConfirmScreen(
                            passcode = passcode,
                            onBackClick = { mainNavController.popBackStack() },
                            onPassed = { mainNavController.popUpTo(NavRoute.TASK_LIST_ROUTE) }
                        )
                    }
                }
            }
        }
    }
}
