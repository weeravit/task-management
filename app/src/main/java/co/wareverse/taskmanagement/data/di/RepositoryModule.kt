package co.wareverse.taskmanagement.data.di

import android.content.SharedPreferences
import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.data.api.APIService
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import co.wareverse.taskmanagement.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideTaskRepository(
        apiService: APIService
    ): TaskRepository {
        return TaskRepository(
            apiService = apiService
        )
    }

    @Singleton
    @Provides
    fun providePasscodeRepository(
        appConfig: AppConfig,
        @TaskManagementPrefs taskManagementPrefs: SharedPreferences,
    ): PasscodeRepository {
        return PasscodeRepository(
            appConfig = appConfig,
            taskManagementPrefs = taskManagementPrefs,
        )
    }
}