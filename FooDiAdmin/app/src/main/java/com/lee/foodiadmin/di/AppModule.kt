package com.lee.foodiadmin.di

import com.lee.foodiadmin.common.SEARCH_FOOD_TARGET_URL
import com.lee.foodiadmin.data.repository.FooDiRepository
import com.lee.foodiadmin.data.rest.RestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun providePictureApi(okHttpClient: OkHttpClient): RestService {
        return Retrofit.Builder()
            .baseUrl(SEARCH_FOOD_TARGET_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(restService: RestService) : FooDiRepository {
        return FooDiRepository(restService)
    }
}