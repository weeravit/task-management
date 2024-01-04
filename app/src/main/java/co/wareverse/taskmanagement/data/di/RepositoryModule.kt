package co.wareverse.taskmanagement.data.di

import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.core.di.IODispatcher
import co.wareverse.taskmanagement.data.api.APIService
import co.wareverse.taskmanagement.data.local.AppDatabase
import co.wareverse.taskmanagement.data.local.AppPreferences
import co.wareverse.taskmanagement.data.repository.PasscodeRepository
import co.wareverse.taskmanagement.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideTaskRepository(
        apiService: APIService,
        appDatabase: AppDatabase,
        appConfig: AppConfig,
        @IODispatcher dispatcher: CoroutineDispatcher,
    ): TaskRepository {
        return TaskRepository(
            apiService = apiService,
            appDatabase = appDatabase,
            appConfig = appConfig,
            dispatcher = dispatcher,
        )
    }

    @Singleton
    @Provides
    fun providePasscodeRepository(
        appConfig: AppConfig,
        appPreferences: AppPreferences,
    ): PasscodeRepository {
        return PasscodeRepository(
            appConfig = appConfig,
            appPreferences = appPreferences,
        )
    }
}