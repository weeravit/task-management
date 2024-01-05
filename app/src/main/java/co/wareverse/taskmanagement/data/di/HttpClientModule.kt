package co.wareverse.taskmanagement.data.di

import co.wareverse.taskmanagement.core.di.AppConfig
import co.wareverse.taskmanagement.data.api.APIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Singleton
    @Provides
    fun provideAPIService(
        appConfig: AppConfig,
    ): APIService {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl(appConfig.baseUrl())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        return retrofit.create(APIService::class.java)
    }
}