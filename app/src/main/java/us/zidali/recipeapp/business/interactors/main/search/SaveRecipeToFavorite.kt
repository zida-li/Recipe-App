package us.zidali.recipeapp.business.interactors.main.search

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import us.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.DataState
import us.zidali.recipeapp.business.domain.util.MessageType
import us.zidali.recipeapp.business.domain.util.Response
import us.zidali.recipeapp.business.domain.util.UIComponentType

class SaveRecipeToFavorite(
    private val favoriteRecipeDao: FavoriteRecipeDao,
) {

    fun execute(
        recipe: Recipe
    ): Flow<DataState<Recipe>> = flow {

        try {
            favoriteRecipeDao.insertRecipe(recipe.toFavoriteEntity())
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