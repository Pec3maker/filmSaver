package com.example.inostudioTask.di

import com.example.inostudioTask.common.FilmRepository
import com.example.inostudioTask.common.FilmRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsFilmRepository(
        filmRepositoryImpl: FilmRepositoryImpl,
    ): FilmRepository
}