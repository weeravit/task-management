package co.wareverse.taskmanagement.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.core.extension.nowInMillis
import co.wareverse.taskmanagement.data.di.TaskManagementPrefs
import javax.inject.Inject

private const val PASSCODE = "PASSCODE"
private const val EXPIRE_IN_MILLIS = "EXPIRE_IN_MILLIS"

class PasscodeRepository @Inject constructor(
    private val appConfig: AppConfig,
    @TaskManagementPrefs private val taskManagementPrefs: SharedPreferences,
) {
    fun isPasscodeValid(passcode: String): Boolean {
        return taskManagementPrefs.getString(
            PASSCODE,
            appConfig.defaultPasscode(),
        ).let {
            passcode == it
        }
    }

    fun isInactive(): Boolean {
        return taskManagementPrefs.getLong(
            EXPIRE_IN_MILLIS,
            nowInMillis().plus(appConfig.inactiveTimeLimitInMillis())
        ).let {
            nowInMillis() > it
        }
    }

    fun extendInactiveTime() {
        taskManagementPrefs.edit {
            putLong(
                EXPIRE_IN_MILLIS,
                nowInMillis().plus(appConfig.inactiveTimeLimitInMillis())
            )
        }
    }
}