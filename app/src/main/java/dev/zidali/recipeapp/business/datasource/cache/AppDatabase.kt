package dev.zidali.recipeapp.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeEntity
import dev.zidali.recipeapp.business.datasource.cache.main.apikey.ApiKeyDao
import dev.zidali.recipeapp.business.datasource.cache.main.apikey.ApiKeyEntity
import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredientDao


@Database(entities = [
    FavoriteRecipeEntity::class,
    TemporaryRecipeEntity::class,
    ShoppingListEntity::class,
    ShoppingListIngredientEntity::class,
    ApiKeyEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getTemporaryRecipeDao(): TemporaryRecipeDao

    abstract fun getFavoriteRecipeDao(): FavoriteRecipeDao

    abstract fun getShoppingListDao(): ShoppingListDao

    abstract fun getShoppingListIngredientDao(): ShoppingListIngredientDao

    abstract fun getRecipeWithIngredientDao(): RecipeWithIngredientDao

    abstract fun getApiKeyDao(): ApiKeyDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }

}