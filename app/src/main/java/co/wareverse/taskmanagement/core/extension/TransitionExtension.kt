package co.wareverse.taskmanagement.core.extension

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween

fun <S> AnimatedContentTransitionScope<S>.enterSlide(
    durationMillis: Int = 500,
    direction: AnimatedContentTransitionScope.SlideDirection = AnimatedContentTransitionScope.SlideDirection.Up,
): EnterTransition {
    return slideIntoContainer(
        animationSpec = tween(durationMillis),
        towards = direction,
    )
}

fun <S> AnimatedContentTransitionScope<S>.exitSlide(
    durationMillis: Int = 500,
    direction: AnimatedContentTransitionScope.SlideDirection = AnimatedContentTransitionScope.SlideDirection.Up,
): ExitTransition {
    return slideOutOfContainer(
        animationSpec = tween(durationMillis),
        towards = direction,
    )
}

fun <S> AnimatedContentTransitionScope<S>.enterSlideUp(durationMillis: Int = 500): EnterTransition {
    return enterSlide(
        durationMillis = durationMillis,
        direction = AnimatedContentTransitionScope.SlideDirection.Up,
    )
}

fun <S> AnimatedContentTransitionScope<S>.exitSlideUp(durationMillis: Int = 500): ExitTransition {
    return exitSlide(
        durationMillis = durationMillis,
        direction = AnimatedContentTransitionScope.SlideDirection.Up,
    )
}

fun <S> AnimatedContentTransitionScope<S>.popEnterSlideDown(durationMillis: Int = 500): EnterTransition {
    return enterSlide(
        durationMillis = durationMillis,
        direction = AnimatedContentTransitionScope.SlideDirection.Down,
    )
}

fun <S> AnimatedContentTransitionScope<S>.popExitSlideDown(durationMillis: Int = 500): ExitTransition {
    return exitSlide(
        durationMillis = durationMillis,
        direction = AnimatedContentTransitionScope.SlideDirection.Down,
    )
}

fun <S> AnimatedContentTransitionScope<S>.enterSlideLeft(durationMillis: Int = 500): EnterTransition {
    return enterSlide(
        durationMillis = durationMillis,
        direction = AnimatedContentTransitionScope.SlideDirection.Left,
    )
}

fun <S> AnimatedContentTransitionScope<S>.exitSlideLeft(durationMillis: Int = 500): ExitTransition {
    return exitSlide(
        durationMillis = durationMillis,
        direction = AnimatedContentTransitionScope.SlideDirection.Left,
    )
}

fun <S> AnimatedContentTransitionScope<S>.popEnterSlideRight(durationMillis: Int = 500): EnterTransition {
    return enterSlide(
        durationMillis = durationMillis,
        direction = AnimatedContentTransitionScope.SlideDirection.Right,
    )
}

fun <S> AnimatedContentTransitionScope<S>.popExitSlideRight(durationMillis: Int = 500): ExitTransition {
    return exitSlide(
        durationMillis = durationMillis,
        direction = AnimatedContentTransitionScope.SlideDirection.Right,
    )
}