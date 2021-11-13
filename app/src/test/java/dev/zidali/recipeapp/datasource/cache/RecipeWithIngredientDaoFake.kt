package dev.zidali.recipeapp.datasource.cache

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredient
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredientDao

class RecipeWithIngredientDaoFake(
    private val db: AppDatabaseFake
): RecipeWithIngredientDao {

    override suspend fun getRecipeWithIngredient(): MutableList<RecipeWithIngredient> {
        return db.recipeWithIngredient
    }

}