package dev.zidali.recipeapp.business.interactors.main.search.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.search.toTemporaryEntity
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState
import dev.zidali.recipeapp.business.domain.util.MessageType
import dev.zidali.recipeapp.business.domain.util.Response
import dev.zidali.recipeapp.business.domain.util.UIComponentType
import dev.zidali.recipeapp.presentation.main.search.list.SearchState
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