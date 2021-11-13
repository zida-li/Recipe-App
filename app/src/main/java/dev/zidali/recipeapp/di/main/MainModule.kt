package dev.zidali.recipeapp.di.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import dev.zidali.recipeapp.business.datasource.cache.AppDatabase
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.apikey.ApiKeyDao
import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredientDao
import dev.zidali.recipeapp.business.datasource.network.main.MainService
import dev.zidali.recipeapp.business.interactors.main.apikey.FetchApiKey
import dev.zidali.recipeapp.business.interactors.main.apikey.UpdateApiKey
import dev.zidali.recipeapp.business.interactors.main.favorite.list.DeleteMultipleRecipesFromFavorite
import dev.zidali.recipeapp.business.interactors.main.favorite.detail.FetchFavoriteRecipe
import dev.zidali.recipeapp.business.interactors.main.favorite.list.FetchFavoriteRecipes
import dev.zidali.recipeapp.business.interactors.main.search.SaveRecipeToFavorite
import dev.zidali.recipeapp.business.interactors.main.search.detail.FetchSearchRecipe
import dev.zidali.recipeapp.business.interactors.main.search.list.CompareSearchToFavorite
import dev.zidali.recipeapp.business.interactors.main.search.list.SaveRecipeToTemporaryRecipeDb
import dev.zidali.recipeapp.business.interactors.main.search.list.SearchRecipes
import dev.zidali.recipeapp.business.interactors.main.shared.AddToShoppingList
import dev.zidali.recipeapp.business.interactors.main.shared.CompareToShoppingList
import dev.zidali.recipeapp.business.interactors.main.shared.DeleteRecipeFromFavorite
import dev.zidali.recipeapp.business.interactors.main.shared.DeleteRecipeFromShoppingList
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.DeleteMultipleRecipesFromShoppingList
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.FetchShoppingListRecipes
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.SetIsCheckedIngredient
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.UpdateRecipeState
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

    @Singleton
    @Provides
    fun provideFetchApiKey(
        apiKeyDao: ApiKeyDao
    ): FetchApiKey {
        return FetchApiKey(
            apiKeyDao
        )
    }

    @Singleton
    @Provides
    fun provideUpdateApiKey(
        apiKeyDao: ApiKeyDao
    ): UpdateApiKey {
        return UpdateApiKey(
            apiKeyDao
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

    @Singleton
    @Provides
    fun provideApiKeyDao(app: AppDatabase): ApiKeyDao {
        return app.getApiKeyDao()
    }

}