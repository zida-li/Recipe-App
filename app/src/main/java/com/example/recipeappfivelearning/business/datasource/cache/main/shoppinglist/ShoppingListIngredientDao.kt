package com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingListIngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(recipes: List<ShoppingListIngredientEntity>): LongArray

    @Query("SELECT * FROM shoppingListIngredient WHERE label = :label")
    suspend fun getIngredientByLabel(label: String): ShoppingListEntity?

}