package com.example.recipeappfivelearning.business.interactors.main.search.list

import com.example.recipeappfivelearning.business.datasource.cache.main.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.toFavoriteRecipe
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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