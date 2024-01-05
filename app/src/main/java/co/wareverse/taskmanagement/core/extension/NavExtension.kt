package co.wareverse.taskmanagement.core.extension

import androidx.navigation.NavHostController

fun NavHostController.popUpTo(
    route: String,
    inclusive: Boolean = true,
) {
    this.navigate(route = route) {
        popUpTo(route) {
            this.inclusive = inclusive
        }
    }
}