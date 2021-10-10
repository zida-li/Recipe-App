package com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist

import androidx.room.*

@Dao
interface ShoppingListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //if successful returns row #, if not returns -1
    suspend fun insertRecipe(recipe: ShoppingListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<ShoppingListEntity>): LongArray

    @Query("SELECT * FROM shoppingList")
    suspend fun getAllRecipes(): MutableList<ShoppingListEntity>

    @Delete
    suspend fun deleteRecipe(recipe: ShoppingListEntity)

}