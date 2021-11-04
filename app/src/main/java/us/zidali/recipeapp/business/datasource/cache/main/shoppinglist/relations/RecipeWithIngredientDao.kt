package us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RecipeWithIngredientDao {

    @Transaction
    @Query("SELECT * FROM shoppingList")
    suspend fun getRecipeWithIngredient(): MutableList<RecipeWithIngredient>

}