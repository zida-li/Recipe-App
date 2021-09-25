package com.example.recipeappfivelearning.di

import android.app.Application
import androidx.room.Room
import com.example.recipeappfivelearning.business.datasource.cache.AppDatabase
import com.example.recipeappfivelearning.business.datasource.cache.AppDatabase.Companion.DATABASE_NAME
import com.example.recipeappfivelearning.business.datasource.datastore.AppDataStore
import com.example.recipeappfivelearning.business.datasource.datastore.AppDataStoreManager
import com.example.recipeappfivelearning.business.domain.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDataStoreManager(
        app: Application
    ): AppDataStore {
        return AppDataStoreManager(app)
    }

}