package dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations

import androidx.room.*
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListEntity

@Dao
interface RecipeWithIngredientDao {

    @Transaction
    @Query("SELECT * FROM shoppingList")
    suspend fun getRecipeWithIngredient(): MutableList<RecipeWithIngredient>

}