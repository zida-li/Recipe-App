package dev.zidali.recipeapp.datasource.cache

import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeEntity

class TemporaryRecipeDaoFake(
    private val db: AppDatabaseFake
): TemporaryRecipeDao {

    override suspend fun insertRecipe(recipe: TemporaryRecipeEntity): Long {
        db.temporaryRecipe.removeIf {
            it.recipeId == recipe.recipeId
        }
        db.temporaryRecipe.add(recipe)
        return 1
    }

    override suspend fun getRecipeByLabel(label: String): TemporaryRecipeEntity? {
        for(recipe in db.temporaryRecipe) {
            if(recipe.recipeName == label) {
                return recipe
            }
        }
        return null
    }

    override suspend fun deleteAllRecipes() {
        for(recipe in db.temporaryRecipe) {
            db.temporaryRecipe.remove(recipe)
            break
        }
    }

    override suspend fun getAllRecipes(): MutableList<TemporaryRecipeEntity> {
        return db.temporaryRecipe
    }


}