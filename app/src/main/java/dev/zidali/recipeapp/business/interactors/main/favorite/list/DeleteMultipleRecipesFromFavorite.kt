package dev.zidali.recipeapp.business.interactors.main.favorite.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState
import dev.zidali.recipeapp.business.domain.util.MessageType
import dev.zidali.recipeapp.business.domain.util.Response
import dev.zidali.recipeapp.business.domain.util.UIComponentType

class DeleteMultipleRecipesFromFavorite (
    private val favoriteRecipeDao: FavoriteRecipeDao,
) {

    fun execute(
        recipes: List<Recipe>
    ): Flow<DataState<Recipe>> = flow {

        try {
            for (recipe in recipes) {
                favoriteRecipeDao.deleteRecipe(recipe.toFavoriteEntity())
            }
        } catch (e: Exception) {
            emit(
                DataState.error<Recipe>(
                    response = Response(
                        message = "DeleteMultipleRecipesFromFavorite: Error Deleting Recipes",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }

    }

}