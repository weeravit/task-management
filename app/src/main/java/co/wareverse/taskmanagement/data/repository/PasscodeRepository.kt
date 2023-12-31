package co.wareverse.taskmanagement.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.core.extension.nowInMillis
import co.wareverse.taskmanagement.data.di.AppPrefs
import javax.inject.Inject

private const val PASSCODE = "PASSCODE"
private const val EXPIRE_IN_MILLIS = "EXPIRE_IN_MILLIS"

class PasscodeRepository @Inject constructor(
    private val appConfig: AppConfig,
    @AppPrefs private val appPrefs: SharedPreferences,
) {
    fun isSetup(): Boolean {
        return appPrefs.getString(
            PASSCODE,
            null,
        ).orEmpty().isNotEmpty()
    }

    fun setup(passcode: String, confirmPasscode: String) {
        takeUnless { passcode != confirmPasscode } ?: throw Exception()

        appPrefs.edit {
            putString(
                PASSCODE,
                passcode,
            )
        }
    }

    fun isPasscodeValid(passcode: String): Boolean {
        return appPrefs.getString(
            PASSCODE,
            appConfig.defaultPasscode(),
        ).let {
            passcode == it
        }
    }

    fun isInactive(): Boolean {
        return appPrefs.getLong(
            EXPIRE_IN_MILLIS,
            nowInMillis().plus(appConfig.inactiveTimeLimitInMillis())
        ).let {
            nowInMillis() > it
        }
    }

    fun extendInactiveTime() {
        appPrefs.edit {
            putLong(
                EXPIRE_IN_MILLIS,
                nowInMillis().plus(appConfig.inactiveTimeLimitInMillis())
            )
        }
    }
}