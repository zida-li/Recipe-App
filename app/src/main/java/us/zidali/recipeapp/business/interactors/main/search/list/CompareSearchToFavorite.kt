package us.zidali.recipeapp.business.interactors.main.search.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import us.zidali.recipeapp.business.datasource.cache.main.toFavoriteRecipe
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.DataState

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