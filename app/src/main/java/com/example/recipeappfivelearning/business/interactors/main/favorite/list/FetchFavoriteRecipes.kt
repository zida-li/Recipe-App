package com.example.recipeappfivelearning.business.interactors.main.favorite.list

import com.example.recipeappfivelearning.business.datasource.cache.main.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.toFavoriteRecipe
import com.example.recipeappfivelearning.business.datasource.network.handleUseCaseException
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FetchFavoriteRecipes (
    private val favoriteRecipeDao: FavoriteRecipeDao
) {

    fun execute(): Flow<DataState<MutableList<Recipe>>> = flow {

        val cacheCall = favoriteRecipeDao.getAllRecipes().map {
            it.toFavoriteRecipe()
        }

        emit(
            DataState.data(
                response = null,
                data = cacheCall.toMutableList()
            )
        )

    }.catch { e->
        emit(handleUseCaseException(e))
    }

}