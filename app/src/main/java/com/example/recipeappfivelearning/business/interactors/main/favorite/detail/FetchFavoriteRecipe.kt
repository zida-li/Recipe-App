package com.example.recipeappfivelearning.business.interactors.main.favorite.detail

import android.util.Log
import com.example.recipeappfivelearning.business.datasource.cache.main.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.toFavoriteRecipe
import com.example.recipeappfivelearning.business.datasource.network.handleUseCaseException
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FetchFavoriteRecipe (
    private val favoriteRecipeDao: FavoriteRecipeDao,
) {

    fun execute(
        recipeName: String
    ): Flow<DataState<Recipe>> = flow {

        val cacheCall = favoriteRecipeDao.getRecipeByLabel(recipeName)?.toFavoriteRecipe()

        emit(
            DataState.data(
                response = null,
                data = cacheCall
            ))

    }.catch { e->
        emit(handleUseCaseException(e))
    }

}