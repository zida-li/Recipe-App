package dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist

import androidx.room.*

@Dao
interface ShoppingListIngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(recipe: ShoppingListIngredientEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(recipes: List<ShoppingListIngredientEntity>): LongArray

    @Query("SELECT * FROM shoppingListIngredient")
    suspend fun getAllIngredients(): MutableList<ShoppingListIngredientEntity>

    @Delete
    suspend fun deleteRecipe(recipe: ShoppingListIngredientEntity)

}