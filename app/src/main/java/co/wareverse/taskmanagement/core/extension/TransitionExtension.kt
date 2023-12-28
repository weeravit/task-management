package co.wareverse.taskmanagement.core.extension

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween

fun <S> AnimatedContentTransitionScope<S>.enterSlideUp(durationMillis: Int = 500): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(durationMillis)
    )
}

fun <S> AnimatedContentTransitionScope<S>.exitSlideUp(durationMillis: Int = 500): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(durationMillis)
    )
}

fun <S> AnimatedContentTransitionScope<S>.popEnterSlideDown(durationMillis: Int = 500): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(durationMillis)
    )
}

fun <S> AnimatedContentTransitionScope<S>.popExitSlideDown(durationMillis: Int = 500): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(durationMillis)
    )
}