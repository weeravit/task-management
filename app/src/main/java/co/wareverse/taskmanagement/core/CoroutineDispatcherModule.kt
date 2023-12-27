package co.wareverse.taskmanagement.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {
    @MainDispatcher
    @Provides
    fun provideMainDispatcher() : CoroutineDispatcher {
        return Dispatchers.Main
    }

    @IODispatcher
    @Provides
    fun provideIODispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher() : CoroutineDispatcher {
        return Dispatchers.Default
    }
}