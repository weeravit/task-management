package co.wareverse.taskmanagement.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeUp
import co.wareverse.taskmanagement.MainE2ETest
import co.wareverse.taskmanagement.data.model.TaskStatus
import co.wareverse.taskmanagement.presentation.task_list.TaskListScreenTestTags

fun MainE2ETest.scrollUp() {
    composeTestRule.onNodeWithTag(TaskListScreenTestTags.TASK_LIST)
        .performTouchInput { swipeUp() }
}

fun MainE2ETest.scrollDown() {
    composeTestRule.onNodeWithTag(TaskListScreenTestTags.TASK_LIST)
        .performTouchInput { swipeDown() }
}

fun MainE2ETest.changeTab(status: TaskStatus) {
    composeTestRule.onNodeWithTag(status.display).apply {
        assertIsDisplayed()
        performClick()
    }
}

fun MainE2ETest.swipeToDeleteTask() {
    composeTestRule.onAllNodesWithTag(TaskListScreenTestTags.TASK_DETAIL_ITEM)
        .onFirst()
        .performTouchInput { swipeLeft() }
}