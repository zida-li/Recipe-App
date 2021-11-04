package us.zidali.recipeapp.business.interactors.main.search.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import us.zidali.recipeapp.business.datasource.cache.main.search.toTemporaryEntity
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.DataState
import us.zidali.recipeapp.business.domain.util.MessageType
import us.zidali.recipeapp.business.domain.util.Response
import us.zidali.recipeapp.business.domain.util.UIComponentType
import us.zidali.recipeapp.presentation.main.search.list.SearchState
import java.lang.Exception

class SaveRecipeToTemporaryRecipeDb (
    private val temporaryRecipeDao: TemporaryRecipeDao
) {

    fun execute(
        recipe: Recipe
    ): Flow<DataState<SearchState>> = flow {

        try {
            temporaryRecipeDao.insertRecipe(recipe.toTemporaryEntity())
        } catch (e: Exception) {
            emit(
                DataState.error<SearchState>(
                    response = Response(
                        message = "SaveRecipeToTemporaryRecipeDb: Saving to Cache Was Unsuccessful",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }

    }
}