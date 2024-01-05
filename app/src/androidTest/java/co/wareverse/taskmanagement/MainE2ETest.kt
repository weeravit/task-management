package co.wareverse.taskmanagement

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.e2e.changeTab
import co.wareverse.taskmanagement.e2e.confirmSetupPasscode
import co.wareverse.taskmanagement.e2e.enterPasscodeLock
import co.wareverse.taskmanagement.e2e.resetPasscode
import co.wareverse.taskmanagement.e2e.scrollDown
import co.wareverse.taskmanagement.e2e.scrollUp
import co.wareverse.taskmanagement.e2e.setupPasscode
import co.wareverse.taskmanagement.e2e.swipeToDeleteTask
import co.wareverse.taskmanagement.e2e.waitTaskListDisplay
import co.wareverse.taskmanagement.e2e.waitUntilPasscodeLock
import co.wareverse.taskmanagement.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainE2ETest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var appConfig: AppConfig

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun main() {
        // Setup a new Passcode
        setupPasscode()
        confirmSetupPasscode()

        // Reset Passcode
        waitUntilPasscodeLock()
        resetPasscode()
        setupPasscode()
        confirmSetupPasscode()

        // Enter Passcode Lock
        waitUntilPasscodeLock()
        enterPasscodeLock()

        // Task List
        changeTab(TaskStatus.TODO)
        waitTaskListDisplay()
        swipeToDeleteTask()
        changeTab(TaskStatus.DOING)
        waitTaskListDisplay()
        swipeToDeleteTask()
        changeTab(TaskStatus.DONE)
        waitTaskListDisplay()
        swipeToDeleteTask()
    }
}