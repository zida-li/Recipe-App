package com.example.recipeappfivelearning.di.main

import com.example.recipeappfivelearning.business.datasource.cache.AppDatabase
import com.example.recipeappfivelearning.business.datasource.cache.main.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.search.TemporaryRecipeDao
import com.example.recipeappfivelearning.business.datasource.network.main.MainService
import com.example.recipeappfivelearning.business.interactors.main.favorite.list.FetchFavoriteRecipes
import com.example.recipeappfivelearning.business.interactors.main.DeleteRecipeFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.favorite.detail.DeleteMultipleRecipesFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.favorite.detail.FetchFavoriteRecipe
import com.example.recipeappfivelearning.business.interactors.main.search.SaveRecipeToFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.detail.FetchSearchRecipe
import com.example.recipeappfivelearning.business.interactors.main.search.list.CompareSearchToFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.list.SaveRecipeToTemporaryRecipeDb
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
        mainService: MainService,
        favoriteRecipeDao: FavoriteRecipeDao
    ): SearchRecipes {
        return SearchRecipes(
            mainService,
            favoriteRecipeDao
        )
    }

    @Singleton
    @Provides
    fun provideSaveRecipeToTemporaryRecipeDb(
        temporaryRecipeDao: TemporaryRecipeDao
    ): SaveRecipeToTemporaryRecipeDb {
        return SaveRecipeToTemporaryRecipeDb(
            temporaryRecipeDao
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

    @Singleton
    @Provides
    fun provideSaveRecipeToFavorite (
        favoriteRecipeDao: FavoriteRecipeDao
    ): SaveRecipeToFavorite {
        return SaveRecipeToFavorite(
            favoriteRecipeDao
        )
    }

    @Singleton
    @Provides
    fun provideDeleteRecipeFromFavorite (
        favoriteRecipeDao: FavoriteRecipeDao
    ): DeleteRecipeFromFavorite {
        return DeleteRecipeFromFavorite(
            favoriteRecipeDao
        )
    }

    @Singleton
    @Provides
    fun provideCompareSearchToFavorite(
        favoriteRecipeDao: FavoriteRecipeDao
    ): CompareSearchToFavorite {
        return CompareSearchToFavorite(
            favoriteRecipeDao
        )
    }

    @Singleton
    @Provides
    fun provideFetchFavoriteRecipes(
        favoriteRecipeDao: FavoriteRecipeDao
    ): FetchFavoriteRecipes {
        return FetchFavoriteRecipes(
            favoriteRecipeDao
        )
    }

    @Singleton
    @Provides
    fun provideFetchFavoriteRecipe(
        favoriteRecipeDao: FavoriteRecipeDao
    ): FetchFavoriteRecipe {
        return FetchFavoriteRecipe (
            favoriteRecipeDao
        )
    }

    @Singleton
    @Provides
    fun provideDeleteMultipleRecipesFromFavorite(
        favoriteRecipeDao: FavoriteRecipeDao
    ): DeleteMultipleRecipesFromFavorite {
        return DeleteMultipleRecipesFromFavorite(
            favoriteRecipeDao
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
    fun provideFavoriteRecipeDao(app: AppDatabase): FavoriteRecipeDao {
        return app.getFavoriteRecipeDao()
    }

}