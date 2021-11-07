package dev.zidali.recipeapp.datasource.cache

import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeEntity

class FavoriteRecipeDaoFake(
    private val db: AppDatabaseFake
): FavoriteRecipeDao {

    override suspend fun insertRecipe(recipe: FavoriteRecipeEntity): Long {
        db.favoriteRecipe.removeIf {
            it.recipeId == recipe.recipeId
        }
        db.favoriteRecipe.add(recipe)
        return 1
    }

    override suspend fun insertRecipes(recipes: List<FavoriteRecipeEntity>): LongArray {
        val longArray = longArrayOf(recipes.size.toLong())
        for (recipe in recipes) {
            db.favoriteRecipe.removeIf {
                it.recipeId == recipe.recipeId
            }
            db.favoriteRecipe.add(recipe)
        }
        longArray.all {
            true
        }
        return longArray
    }

    override suspend fun getRecipeByLabel(label: String): FavoriteRecipeEntity? {
        for(recipe in db.favoriteRecipe) {
            if(recipe.recipeName == label) {
                return recipe
            }
        }
        return null
    }

    override suspend fun getAllRecipes(): MutableList<FavoriteRecipeEntity> {
        return db.favoriteRecipe
    }

    override suspend fun deleteAllRecipes() {
        for(recipe in db.favoriteRecipe) {
            db.favoriteRecipe.remove(recipe)
        }
    }

    override suspend fun deleteRecipe(recipe: FavoriteRecipeEntity) {
        db.favoriteRecipe.remove(recipe)
    }

    override suspend fun searchRecipeOrderByDateDESC(): MutableList<FavoriteRecipeEntity> {
        val copy = db.favoriteRecipe.toMutableList()
        copy.sortByDescending { it.timeSaved }
        return copy
    }


}