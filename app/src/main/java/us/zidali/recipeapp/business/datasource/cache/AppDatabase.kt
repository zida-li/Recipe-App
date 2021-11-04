package us.zidali.recipeapp.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import us.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import us.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeEntity
import us.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import us.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeEntity
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListEntity
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientEntity
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredientDao


@Database(entities = [
    FavoriteRecipeEntity::class,
    TemporaryRecipeEntity::class,
    ShoppingListEntity::class,
    ShoppingListIngredientEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getTemporaryRecipeDao(): TemporaryRecipeDao

    abstract fun getFavoriteRecipeDao(): FavoriteRecipeDao

    abstract fun getShoppingListDao(): ShoppingListDao

    abstract fun getShoppingListIngredientDao(): ShoppingListIngredientDao

    abstract fun getRecipeWithIngredientDao(): RecipeWithIngredientDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }

}