package com.example.recipeappfivelearning.di.main

import com.example.recipeappfivelearning.business.datasource.cache.AppDatabase
import com.example.recipeappfivelearning.business.datasource.cache.main.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.search.TemporaryRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.ShoppingListDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredientDao
import com.example.recipeappfivelearning.business.datasource.network.main.MainService
import com.example.recipeappfivelearning.business.interactors.main.favorite.list.FetchFavoriteRecipes
import com.example.recipeappfivelearning.business.interactors.main.shared.DeleteRecipeFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.shared.DeleteRecipeFromShoppingList
import com.example.recipeappfivelearning.business.interactors.main.shared.CompareToShoppingList
import com.example.recipeappfivelearning.business.interactors.main.shared.AddToShoppingList
import com.example.recipeappfivelearning.business.interactors.main.favorite.detail.DeleteMultipleRecipesFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.favorite.detail.FetchFavoriteRecipe
import com.example.recipeappfivelearning.business.interactors.main.search.SaveRecipeToFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.detail.FetchSearchRecipe
import com.example.recipeappfivelearning.business.interactors.main.search.list.CompareSearchToFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.list.SaveRecipeToTemporaryRecipeDb
import com.example.recipeappfivelearning.business.interactors.main.search.list.SearchRecipes
import com.example.recipeappfivelearning.business.interactors.main.shoppinglist.DeleteMultipleRecipesFromShoppingList
import com.example.recipeappfivelearning.business.interactors.main.shoppinglist.FetchShoppingListRecipes
import com.example.recipeappfivelearning.business.interactors.main.shoppinglist.SetIsCheckedIngredient
import com.example.recipeappfivelearning.business.interactors.main.shoppinglist.UpdateRecipeState
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

    @Singleton
    @Provides
    fun provideFetchShoppingListRecipes(
        recipeWithIngredientDao: RecipeWithIngredientDao,
    ): FetchShoppingListRecipes {
        return FetchShoppingListRecipes(
            recipeWithIngredientDao
        )
    }

    @Singleton
    @Provides
    fun provideAddToShoppingList(
        shoppingListDao: ShoppingListDao,
        shoppingListIngredientDao: ShoppingListIngredientDao,
    ): AddToShoppingList {
        return AddToShoppingList(
            shoppingListDao,
            shoppingListIngredientDao
        )
    }

    @Singleton
    @Provides
    fun provideDeleteMultipleRecipesFromShoppingList(
        shoppingListDao: ShoppingListDao,
        shoppingListIngredientDao: ShoppingListIngredientDao
    ): DeleteMultipleRecipesFromShoppingList {
        return DeleteMultipleRecipesFromShoppingList(
            shoppingListDao,
            shoppingListIngredientDao
        )
    }

    @Singleton
    @Provides
    fun provideSetIsCheckedIngredient(
        shoppingListIngredientDao: ShoppingListIngredientDao
    ): SetIsCheckedIngredient {
        return SetIsCheckedIngredient(
            shoppingListIngredientDao
        )
    }

    @Singleton
    @Provides
    fun provideSetIsExpandedRecipe(
        shoppingListDao: ShoppingListDao
    ): UpdateRecipeState {
        return UpdateRecipeState(
            shoppingListDao
        )
    }

    @Singleton
    @Provides
    fun provideCompareFavoriteToShoppingList(
        shoppingListDao: ShoppingListDao
    ): CompareToShoppingList {
        return CompareToShoppingList(
            shoppingListDao
        )
    }

    @Singleton
    @Provides
    fun provideDeleteRecipeFromShoppingList(
        shoppingListDao: ShoppingListDao
    ): DeleteRecipeFromShoppingList {
        return DeleteRecipeFromShoppingList (
            shoppingListDao
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

    @Singleton
    @Provides
    fun provideShoppingListDao(app: AppDatabase): ShoppingListDao {
        return app.getShoppingListDao()
    }

    @Singleton
    @Provides
    fun provideShoppingListIngredientDao(app: AppDatabase): ShoppingListIngredientDao {
        return app.getShoppingListIngredientDao()
    }

    @Singleton
    @Provides
    fun provideRecipeWithIngredientDao(app: AppDatabase): RecipeWithIngredientDao {
        return app.getRecipeWithIngredientDao()
    }

}