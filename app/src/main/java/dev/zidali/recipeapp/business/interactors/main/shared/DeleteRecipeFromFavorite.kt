package dev.zidali.recipeapp.business.interactors.main.shared

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState
import dev.zidali.recipeapp.business.domain.util.MessageType
import dev.zidali.recipeapp.business.domain.util.Response
import dev.zidali.recipeapp.business.domain.util.UIComponentType

class DeleteRecipeFromFavorite (
    private val favoriteRecipeDao: FavoriteRecipeDao,
) {

    fun execute(
        recipe: Recipe
    ): Flow<DataState<Recipe>> = flow {

        try {
            favoriteRecipeDao.deleteRecipe(recipe.toFavoriteEntity())
        } catch (e: Exception) {
            emit(
                DataState.error<Recipe>(
                    response = Response(
                        message = "SaveRecipeToFavorite: saving was Unsuccessful",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }

    }

}