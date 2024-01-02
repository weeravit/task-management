package co.wareverse.taskmanagement.data.repository

import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.core.extension.nowInMillis
import co.wareverse.taskmanagement.data.exception.PasscodeMismatchException
import co.wareverse.taskmanagement.data.local.AppPreferences
import javax.inject.Inject

class PasscodeRepository @Inject constructor(
    private val appConfig: AppConfig,
    private val appPreferences: AppPreferences,
) {
    fun isSetup(): Boolean {
        return appPreferences.getPasscode().orEmpty().isNotEmpty()
    }

    fun setup(passcode: String, confirmPasscode: String) {
        takeUnless { passcode != confirmPasscode } ?: throw PasscodeMismatchException()

        appPreferences.setPasscode(passcode)
    }

    fun isPasscodeValid(passcode: String): Boolean {
        return appPreferences.getPasscode().orEmpty()
            .let { it == passcode }
    }

    fun isInactive(): Boolean {
        val inactiveTime = appPreferences.getInactiveTime()
            ?: nowInMillis().plus(appConfig.inactiveTimeLimitInMillis())
        return nowInMillis() > inactiveTime
    }

    fun extendInactiveTime(): Long {
        val newInactiveTime = nowInMillis().plus(appConfig.inactiveTimeLimitInMillis())

        appPreferences.setInactiveTime(
            newInactiveTime
        )

        return newInactiveTime
    }
}