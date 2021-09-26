package com.example.recipeappfivelearning.business.interactors.main.search.detail

import com.example.recipeappfivelearning.business.datasource.cache.main.search.TemporaryRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.search.toRecipe
import com.example.recipeappfivelearning.business.datasource.network.handleUseCaseException
import com.example.recipeappfivelearning.business.domain.util.DataState
import com.example.recipeappfivelearning.presentation.main.search.detail.ViewRecipeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FetchSearchRecipe (
    private val temporaryRecipeDao: TemporaryRecipeDao
) {

    fun execute(
        recipeName: String
    ): Flow<DataState<ViewRecipeState>> = flow {

        val cacheCall = temporaryRecipeDao.getRecipeByLabel(recipeName).toRecipe()

        temporaryRecipeDao.deleteAllRecipes()

        val viewState = ViewRecipeState(
            recipe = cacheCall
        )

        emit(
            DataState.data(
                response = null,
                data = viewState
            )
        )

    }.catch { e->
        emit(handleUseCaseException(e))
    }

}