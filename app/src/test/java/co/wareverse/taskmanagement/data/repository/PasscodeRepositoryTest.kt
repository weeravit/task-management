package co.wareverse.taskmanagement.data.repository

import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.core.extension.nowInMillis
import co.wareverse.taskmanagement.data.exception.PasscodeMismatchException
import co.wareverse.taskmanagement.data.local.AppPreferences
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PasscodeRepositoryTest {
    private lateinit var appConfig: AppConfig
    private lateinit var appPreferences: AppPreferences
    private lateinit var passcodeRepository: PasscodeRepository

    @Before
    fun setup() {
        appConfig = mock()
        appPreferences = mock()
        passcodeRepository = PasscodeRepository(
            appConfig = appConfig,
            appPreferences = appPreferences,
        )
    }

    @Test
    fun `should return false when passcode is never set up`() {
        // Given
        whenever(appPreferences.getPasscode()).thenReturn(null)

        // When
        val result = passcodeRepository.isSetup()

        // Then
        assertEquals(false, result)
    }

    @Test
    fun `should return true when passcode is set up`() {
        // Given
        whenever(appPreferences.getPasscode()).thenReturn("1234")

        // When
        val result = passcodeRepository.isSetup()

        // Then
        assertEquals(true, result)
    }

    @Test
    fun `should set passcode correctly`() {
        // Given
        val passcode = "1234"

        // When
        passcodeRepository.setup(passcode, passcode)

        // Then
        val expectedPasscode = "1234"
        verify(appPreferences).setPasscode(expectedPasscode)
    }

    @Test
    fun `should throw exception on mismatch passcode setup`() {
        // Given
        val correctPasscode = "1234"
        val mismatchedPasscode = "5678"

        // When
        val exception = assertThrows(PasscodeMismatchException::class.java) {
            passcodeRepository.setup(correctPasscode, mismatchedPasscode)
        }

        // Then
        assertNotNull(exception)
    }

    @Test
    fun `should return true when passcode is valid`() {
        // Given
        whenever(appPreferences.getPasscode()).thenReturn("1234")

        // When
        val result = passcodeRepository.isPasscodeValid("1234")

        // Then
        assertEquals(true, result)
    }

    @Test
    fun `should return false when passcode is invalid`() {
        // Given
        whenever(appPreferences.getPasscode()).thenReturn("1234")

        // When
        val result = passcodeRepository.isPasscodeValid("5678")

        // Then
        assertEquals(false, result)
    }

    @Test
    fun `should return true when inactive time is in past`() {
        // Given
        val currentTime = 1_000L
        whenever(appPreferences.getInactiveTime()).thenReturn(currentTime - 500)

        // When
        val result = passcodeRepository.isInactive()

        // Then
        assertEquals(true, result)
    }

    @Test
    fun `should return false when inactive time is in future`() {
        // Given
        val expireTime = nowInMillis().plus(10_000)
        whenever(appPreferences.getInactiveTime()).thenReturn(expireTime)

        // When
        val result = passcodeRepository.isInactive()

        // Then
        assertEquals(false, result)
    }

    @Test
    fun `should extend inactive time`() {
        // Given - No specific setup required in this scenario

        // When
        passcodeRepository.extendInactiveTime()

        // Then
        verify(appPreferences).setInactiveTime(any())
    }
}