package com.example.recipeappfivelearning.business.interactors.main.search.list

import com.example.recipeappfivelearning.business.datasource.cache.main.search.TemporaryRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.search.toTemporaryEntity
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import com.example.recipeappfivelearning.business.domain.util.MessageType
import com.example.recipeappfivelearning.business.domain.util.Response
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import com.example.recipeappfivelearning.presentation.main.search.list.SearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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