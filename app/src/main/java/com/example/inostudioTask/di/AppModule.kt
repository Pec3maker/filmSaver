package com.example.inostudioTask.di

import com.example.inostudioTask.common.Constants
import com.example.inostudioTask.data.remote.FilmApi
import com.example.inostudioTask.data.repository.FilmRepositoryImpl
import com.example.inostudioTask.domain.repository.FilmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFilmApi(): FilmApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFilmRepository(api: FilmApi): FilmRepository {
        return FilmRepositoryImpl(api)
    }
}