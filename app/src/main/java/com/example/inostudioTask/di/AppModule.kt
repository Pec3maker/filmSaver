package com.example.inostudioTask.di

import android.content.Context
import androidx.room.Room
import com.example.inostudioTask.data.dataSource.FilmDatabase
import com.example.inostudioTask.data.remote.FilmApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DATABASE_NAME = "filmsDatabase"
    private const val BASE_URL = "https://api.themoviedb.org"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideFilmApi(moshi: Moshi): FilmApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(FilmApi::class.java)

    @Provides
    @Singleton
    fun provideFilmDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(context, FilmDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideFilmDao(db: FilmDatabase) = db.filmDao

    @Provides
    @Singleton
    fun provideActorDao(db: FilmDatabase) = db.actorDao
}