package dev.zidali.recipeapp.business.interactors.main.search.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteRecipe
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState

class CompareSearchToFavorite (
    private val favoriteRecipeDao: FavoriteRecipeDao
) {

    fun execute (
        recipes: MutableList<Recipe>
    ): Flow<DataState<Recipe>> = flow {

        val recipesFromDb = favoriteRecipeDao.getAllRecipes().map {
            it.toFavoriteRecipe()
        }


        for(recipe in recipes) {
            recipe.isFavorite = false
        }

        for(recipe in recipes){
            for(recipeFromDb in recipesFromDb) {
                if(recipe.recipeId == recipeFromDb.recipeId) {
                    recipe.isFavorite = true
                }
            }
        }

    }

}