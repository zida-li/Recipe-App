package dev.zidali.recipeapp.business.interactors.main.favorite.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteRecipe
import dev.zidali.recipeapp.business.datasource.network.handleUseCaseException
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState

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