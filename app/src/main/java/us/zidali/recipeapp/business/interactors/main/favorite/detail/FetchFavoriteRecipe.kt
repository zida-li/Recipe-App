package us.zidali.recipeapp.business.interactors.main.favorite.detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import us.zidali.recipeapp.business.datasource.cache.main.toFavoriteRecipe
import us.zidali.recipeapp.business.datasource.network.handleUseCaseException
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.DataState

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