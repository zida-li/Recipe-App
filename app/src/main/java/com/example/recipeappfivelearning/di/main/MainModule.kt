package com.example.recipeappfivelearning.di.main

import com.example.recipeappfivelearning.business.datasource.cache.AppDatabase
import com.example.recipeappfivelearning.business.datasource.cache.main.search.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.search.TemporaryRecipeDao
import com.example.recipeappfivelearning.business.datasource.network.main.MainService
import com.example.recipeappfivelearning.business.interactors.main.search.detail.FetchSearchRecipe
import com.example.recipeappfivelearning.business.interactors.main.search.list.SearchRecipes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideMainService(retrofitBuilder: Retrofit.Builder): MainService {
        return retrofitBuilder
            .build()
            .create(MainService::class.java)
    }

    /**
     * INTERACTORS
     */
    @Singleton
    @Provides
    fun provideSearchRecipes(
        mainService: MainService
    ): SearchRecipes {
        return SearchRecipes(
            mainService
        )
    }

    @Singleton
    @Provides
    fun provideFetchSearchRecipe(
        temporaryRecipeDao: TemporaryRecipeDao
    ): FetchSearchRecipe {
        return FetchSearchRecipe(
            temporaryRecipeDao
        )
    }


    /**
     * DATABASE
     */
    @Singleton
    @Provides
    fun provideTemporaryRecipeDao(app: AppDatabase): TemporaryRecipeDao{
        return app.getTemporaryRecipeDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteRecipeDao(app: AppDatabase): FavoriteRecipeDao{
        return app.getFavoriteRecipeDao()
    }

}