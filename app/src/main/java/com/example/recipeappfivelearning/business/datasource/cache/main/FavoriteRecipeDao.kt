package com.example.recipeappfivelearning.business.datasource.cache.main

import androidx.room.*

@Dao
interface FavoriteRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //if successful returns row #, if not returns -1
    suspend fun insertRecipe(recipe: FavoriteRecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<FavoriteRecipeEntity>): LongArray

    //select from recipes where a "field" equals some "argument"
    @Query("SELECT * FROM favoriteRecipes WHERE label = :label")
    suspend fun getRecipeByLabel(label: String): FavoriteRecipeEntity?

    @Query("SELECT * FROM favoriteRecipes")
    suspend fun getAllRecipes(): MutableList<FavoriteRecipeEntity>

    @Query("DELETE FROM favoriteRecipes")
    suspend fun deleteAllRecipes()

    @Delete
    suspend fun deleteRecipe(recipe: FavoriteRecipeEntity)

    @Query("SELECT * FROM favoriteRecipes ORDER BY timeSaved DESC")
    suspend fun searchRecipeOrderByDateDESC(): MutableList<FavoriteRecipeEntity>

}