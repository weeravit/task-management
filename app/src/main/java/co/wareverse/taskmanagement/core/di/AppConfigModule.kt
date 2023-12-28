package co.wareverse.taskmanagement.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {
    @Singleton
    @Provides
    fun provideAppConfig(): AppConfig {
        return object : AppConfig {
            override fun baseUrl(): String = "https://todo-list-api-mfchjooefq-as.a.run.app/"
            override fun inactiveTimeLimitInMillis(): Long = 10_000
            override fun defaultPasscode(): String = "123456"
        }
    }
}

interface AppConfig {
    fun baseUrl(): String
    fun inactiveTimeLimitInMillis(): Long
    fun defaultPasscode(): String
}