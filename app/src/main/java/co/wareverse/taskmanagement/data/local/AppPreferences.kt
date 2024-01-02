package co.wareverse.taskmanagement.data.local

import android.content.SharedPreferences
import androidx.core.content.edit

private const val PASSCODE = "PASSCODE"
private const val EXPIRE_IN_MILLIS = "EXPIRE_IN_MILLIS"

interface AppPreferences {
    fun setPasscode(passcode: String)
    fun getPasscode(): String?
    fun setInactiveTime(millis: Long)
    fun getInactiveTime(): Long?
}

class AppPreferencesImpl(
    private val sharedPrefs: SharedPreferences,
) : AppPreferences {
    override fun setPasscode(passcode: String) {
        sharedPrefs.edit {
            putString(
                PASSCODE,
                passcode,
            )
        }
    }

    override fun getPasscode(): String? {
        return sharedPrefs.getString(
            PASSCODE,
            null,
        )
    }

    override fun setInactiveTime(millis: Long) {
        sharedPrefs.edit {
            putLong(
                EXPIRE_IN_MILLIS,
                millis,
            )
        }
    }

    override fun getInactiveTime(): Long? {
        return sharedPrefs.getLong(
            EXPIRE_IN_MILLIS,
            0L,
        ).takeUnless { it == 0L }
    }
}