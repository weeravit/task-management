package co.wareverse.taskmanagement.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import co.wareverse.taskmanagement.MainE2ETest
import co.wareverse.taskmanagement.R
import co.wareverse.taskmanagement.core.extension.nowInMillis

fun MainE2ETest.setupPasscode() {
    composeTestRule.onNodeWithText(
        composeTestRule.activity.getString(R.string.passcode_setup_screen_title_text)
    ).assertIsDisplayed()

    appConfig.defaultPasscode().forEach { code ->
        composeTestRule.onNodeWithText("$code").performClick()
    }
}

fun MainE2ETest.confirmSetupPasscode() {
    composeTestRule.onNodeWithText(
        composeTestRule.activity.getString(R.string.confirm_setup_passcode_screen_title_text)
    ).assertIsDisplayed()

    appConfig.defaultPasscode().forEach { code ->
        composeTestRule.onNodeWithText("$code").performClick()
    }
}

fun MainE2ETest.waitUntilPasscodeLock() {
    val limitInMillis = appConfig.inactiveTimeLimitInMillis()
    val expireTime = nowInMillis().plus(limitInMillis).plus(1000)

    composeTestRule.waitUntil(limitInMillis.plus(2000)) {
        nowInMillis() > expireTime
    }

    composeTestRule.onNodeWithText(
        composeTestRule.activity.getString(R.string.passcode_lock_screen_title_text)
    ).assertIsDisplayed()
}

fun MainE2ETest.enterPasscodeLock() {
    composeTestRule.onNodeWithText(
        composeTestRule.activity.getString(R.string.passcode_lock_screen_title_text)
    ).assertIsDisplayed()

    appConfig.defaultPasscode().forEach { code ->
        composeTestRule.onNodeWithText("$code").performClick()
    }
}

fun MainE2ETest.resetPasscode() {
    composeTestRule.onNodeWithText(
        composeTestRule.activity.getString(R.string.passcode_lock_screen_title_text)
    ).assertIsDisplayed()

    composeTestRule.onNodeWithText(
        composeTestRule.activity.getString(R.string.reset_new_passcode_text)
    ).performClick()
}