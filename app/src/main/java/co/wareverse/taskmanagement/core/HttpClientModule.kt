package co.wareverse.taskmanagement.core

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Singleton
    @Provides
    fun provideAPIService(): APIService {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = MediaType.get("application/json")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://todo-list-api-mfchjooefq-as.a.run.app/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        return retrofit.create(APIService::class.java)
    }
}