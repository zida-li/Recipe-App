package us.zidali.recipeapp.business.interactors.main.favorite.detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import us.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.DataState
import us.zidali.recipeapp.business.domain.util.MessageType
import us.zidali.recipeapp.business.domain.util.Response
import us.zidali.recipeapp.business.domain.util.UIComponentType

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