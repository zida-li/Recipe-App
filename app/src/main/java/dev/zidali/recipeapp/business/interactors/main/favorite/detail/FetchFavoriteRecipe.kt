package dev.zidali.recipeapp.business.interactors.main.favorite.detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteRecipe
import dev.zidali.recipeapp.business.datasource.network.handleUseCaseException
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState

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